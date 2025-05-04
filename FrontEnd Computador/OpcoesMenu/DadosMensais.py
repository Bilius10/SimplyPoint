import requests
import flet as ft
import time
from session import session


def dados_mensais_page(on_menu):
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Campo de CPF
    cpf_value = ft.TextField(
        label="CPF",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),
        border_color="#649ea7",
        text_style=ft.TextStyle(color="#000000"),
    )

    # Campo de Mês
    mes_value = ft.TextField(
        label="Mês",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),
        border_color="#649ea7",
        text_style=ft.TextStyle(color="#000000"),
    )

    # Campo de Ano
    ano_value = ft.TextField(
        label="Ano",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),
        border_color="#649ea7",
        text_style=ft.TextStyle(color="#000000"),
    )

    # Mensagem de resposta
    mensagem_api = ft.Text(
        "",
        font_family="MinhaFonte",
        size=16,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    # Dados do usuário
    dados_usuario = ft.Column(
        [],
        alignment=ft.MainAxisAlignment.CENTER,
        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
        spacing=10,
    )

    textoNome = ft.Text(
        "Usuario",
        font_family="MinhaFonte",
        size=70,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
        color="#649ea7"
    )

    # Função para formatar as horas trabalhadas
    def formatar_horas(horas):
        return horas.replace("PT", "").replace("H", " Horas ").replace("M", " Minutos ").replace("S", " Segundos")

    # Função para enviar os dados
    def enviar(evento):

        headers = {"Authorization": f"Bearer {session.user_data.get('token', '')}"
                   }
        try:
            response = requests.get(
                f"http://localhost:8080/ponto/dadosMensais/{cpf_value.value}", headers=headers
            )

            if response.status_code == 200:
                data = response.json()
                textoNome.value = data.get("nomeUsuario", "Usuário")
                dados_usuario.controls = [
                    ft.Column(
                        [
                            ft.Text("Nome", color="#649ea7", size=20,
                                    weight=ft.FontWeight.BOLD),
                            ft.Text(data['nomeUsuario'],
                                    color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Dias Trabalhados", color="#649ea7",
                                    size=20, weight=ft.FontWeight.BOLD),
                            ft.Text(str(data['diasTrabalhados']),
                                    color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Dias Faltados", color="#649ea7",
                                    size=20, weight=ft.FontWeight.BOLD),
                            ft.Text(str(data['diasFaltados']),
                                    color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Desconto por Falta", color="#649ea7",
                                    size=20, weight=ft.FontWeight.BOLD),
                            ft.Text(
                                f"R$ {data['descontoPorFalta']:.2f}", color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Salário com Desconto", color="#649ea7",
                                    size=20, weight=ft.FontWeight.BOLD),
                            ft.Text(
                                f"R$ {data['salarioComDesconto']:.2f}", color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Cargo", color="#649ea7", size=20,
                                    weight=ft.FontWeight.BOLD),
                            ft.Text(data['cargo'], color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                    ft.Column(
                        [
                            ft.Text("Horas Trabalhadas no Mês", color="#649ea7",
                                    size=20, weight=ft.FontWeight.BOLD),
                            ft.Text(formatar_horas(
                                data['horasTrabalhadasNoMes']), color="#000000", size=18),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                    ),
                ]
                mensagem_api.value = "Dados carregados com sucesso!"
                mensagem_api.color = "#00FF00"
            else:
                mensagem_api.value = response.json().get("mensagem", "Erro desconhecido.")
                mensagem_api.color = "#FF0000"

        except requests.exceptions.RequestException as e:
            mensagem_api.value = f"Erro ao enviar dados: {e}"
            mensagem_api.color = "#eefaa8"

        mensagem_api.update()
        dados_usuario.update()
        textoNome.update()

        time.sleep(1)

        mensagem_api.value = ""
        mensagem_api.update()

    return ft.Container(
        content=ft.Row(
            [
                # Container de Pesquisa
                ft.Container(
                    content=ft.Column(
                        [
                            ft.Container(
                                content=ft.Text(
                                    value="Pesquisar",
                                    color="#649ea7",
                                    font_family="MinhaFonte",
                                    size=50,
                                    weight=ft.FontWeight.BOLD,
                                ),
                                alignment=ft.alignment.center,
                                height=70,  # Altura ajustada para alinhar os títulos
                            ),
                            ft.Divider(height=2, thickness=2, color="#8fbfb9"),
                            cpf_value,
                            mes_value,
                            ano_value,
                            mensagem_api,
                            ft.Container(height=10),
                            ft.ElevatedButton(
                                text="Enviar",
                                bgcolor="#649ea7",
                                color="#ffffff",
                                width=400,
                                height=50,
                                on_click=enviar,
                                style=ft.ButtonStyle(
                                    shape=ft.RoundedRectangleBorder(radius=10),
                                ),
                            ),
                            ft.ElevatedButton(
                                text="Voltar ao Menu",
                                bgcolor="#649ea7",
                                color="#ffffff",
                                width=400,
                                height=50,
                                on_click=on_menu,
                                style=ft.ButtonStyle(
                                    shape=ft.RoundedRectangleBorder(radius=10),
                                ),
                            ),
                        ],
                        alignment=ft.MainAxisAlignment.CENTER,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                        spacing=15,
                    ),
                    width=500,
                    height=700,
                    padding=20,
                    border_radius=ft.border_radius.all(50),
                    alignment=ft.alignment.center,
                    bgcolor="#f0f8ff",
                    image_src=image,  # Imagem de fundo adicionada
                    image_fit=ft.ImageFit.COVER,
                ),
                # Container de Dados do Usuário
                ft.Container(
                    content=ft.Column(
                        [
                            ft.Container(
                                content=textoNome,
                                alignment=ft.alignment.center,  # Alinha o texto ao centro
                            ),
                            ft.Divider(height=2, thickness=2, color="#8fbfb9"),
                            dados_usuario,
                        ],
                        alignment=ft.MainAxisAlignment.START,
                        horizontal_alignment=ft.CrossAxisAlignment.CENTER,  # Alinha o conteúdo ao centro
                        spacing=15,
                    ),
                    width=500,
                    height=700,
                    padding=20,
                    border_radius=ft.border_radius.all(50),
                    alignment=ft.alignment.center,
                    bgcolor="#f0f8ff",
                    image_src=image,  # Imagem de fundo adicionada
                    image_fit=ft.ImageFit.COVER,
                ),
            ],
            alignment=ft.MainAxisAlignment.CENTER,
            spacing=20,
        ),
        alignment=ft.alignment.center,
    )
