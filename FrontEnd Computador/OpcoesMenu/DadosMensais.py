import requests
import flet as ft
from session import session


def dados_mensais_page(on_menu):
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Título da página
    titulo = ft.Text(
        value="Dados Mensais",
        color="#649ea7",  # Cor da paleta
        font_family="MinhaFonte",
        size=40,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    # Campos de texto
    cpf_value = ft.TextField(
        label="CPF",
        width=300,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),
        border_color="#649ea7",
        text_style=ft.TextStyle(color="#000000"),
    )
    mes_value = ft.TextField(
        label="Mês",
        width=300,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),
        border_color="#649ea7",
        text_style=ft.TextStyle(color="#000000"),
    )
    ano_value = ft.TextField(
        label="Ano",
        width=300,
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

    # Quadro para exibir os dados retornados
    dados_quadro = ft.Column(
        [],
        spacing=10,
        alignment=ft.MainAxisAlignment.START,
    )

    # Função para enviar os dados
    def enviar(evento):
        try:
            headers = {
                "Authorization": f"Bearer {session.user_data.get('token', '')}"}
            url = f"http://localhost:8080/ponto/dadosMensais/{cpf_value.value}"
            params = {"mes": mes_value.value, "ano": ano_value.value}

            response = requests.get(url, headers=headers, params=params)

            if response.status_code == 200:
                dados = response.json()
                mensagem_api.value = "Dados carregados com sucesso!"
                mensagem_api.color = "#00FF00"  # Verde

                # Atualiza o quadro com os dados retornados
                dados_quadro.controls = [
                    ft.Text(f"Nome: {dados['nomeUsuario']}", color="#000000"),
                    ft.Text(
                        f"Dias Trabalhados: {dados['diasTrabalhados']}", color="#000000"),
                    ft.Text(
                        f"Dias Faltados: {dados['diasFaltados']}", color="#000000"),
                    ft.Text(
                        f"Desconto por Falta: R$ {dados['descontoPorFalta']:.2f}", color="#000000"),
                    ft.Text(
                        f"Salário com Desconto: R$ {dados['salarioComDesconto']:.2f}", color="#000000"),
                    ft.Text(f"Cargo: {dados['cargo']}", color="#000000"),
                    ft.Text(
                        f"Horas Trabalhadas no Mês: {dados['horasTrabalhadasNoMes']}", color="#000000"),
                ]
            else:
                mensagem_api.value = "Erro ao carregar dados."
                mensagem_api.color = "#FF0000"  # Vermelho

        except Exception as e:
            mensagem_api.value = f"Erro: {e}"
            mensagem_api.color = "#FF0000"  # Vermelho

        mensagem_api.update()
        dados_quadro.update()

    # Botão de enviar
    botao_enviar = ft.ElevatedButton(
        text="Enviar",
        bgcolor="#649ea7",  # Cor da paleta
        color="#ffffff",
        width=300,
        height=50,
        on_click=enviar,
        style=ft.ButtonStyle(
            shape=ft.RoundedRectangleBorder(radius=10),
        ),
    )

    # Botão de voltar
    botao_voltar = ft.ElevatedButton(
        text="Voltar",
        bgcolor="#649ea7",  # Cor da paleta
        color="#ffffff",
        width=300,
        height=50,
        on_click=on_menu,
        style=ft.ButtonStyle(
            shape=ft.RoundedRectangleBorder(radius=10),
        ),
    )

    # Container de pesquisa
    container_pesquisa = ft.Container(
        content=ft.Column(
            [
                cpf_value,
                mes_value,
                ano_value,
                mensagem_api,
                botao_enviar,
                botao_voltar,
            ],
            alignment=ft.MainAxisAlignment.START,
            spacing=15,
        ),
        width=350,
        height=600,
        padding=20,
        border_radius=ft.border_radius.all(10),
        bgcolor="#F5F5F5",  # Fundo claro para destaque
    )

    # Container de dados retornados
    container_dados = ft.Container(
        content=ft.Column(
            [
                ft.Text("Dados Retornados:", color="#649ea7",
                        size=20, weight=ft.FontWeight.BOLD),
                dados_quadro,
            ],
            alignment=ft.MainAxisAlignment.START,
            spacing=15,
        ),
        width=350,
        height=600,
        padding=20,
        border_radius=ft.border_radius.all(10),
        bgcolor="#FFFFFF",  # Fundo branco
    )

    # Layout da tela
    return ft.Container(
        content=ft.Row(
            [
                container_pesquisa,
                container_dados,
            ],
            alignment=ft.MainAxisAlignment.CENTER,
            spacing=20,
        ),
        width=800,
        height=600,
        padding=20,
        border_radius=ft.border_radius.all(50),
        alignment=ft.alignment.center,
        image_src=image,
        image_fit=ft.ImageFit.COVER,
    )
