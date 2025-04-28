import flet as ft
import requests
import time


def register_page(on_login):

    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Campos de entrada
    nome_value = ft.TextField(
        label="Nome",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),  # Cor da paleta
        border_color="#649ea7",  # Cor da paleta
        text_style=ft.TextStyle(color="#000000"),
    )
    senha_value = ft.TextField(
        label="Senha",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),  # Cor da paleta
        border_color="#649ea7",  # Cor da paleta
        text_style=ft.TextStyle(color="#000000"),
        password=True,
    )
    email_value = ft.TextField(
        label="Email",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),  # Cor da paleta
        border_color="#649ea7",  # Cor da paleta
        text_style=ft.TextStyle(color="#000000"),
    )
    cpf_value = ft.TextField(
        label="CPF",
        width=400,
        height=50,
        label_style=ft.TextStyle(color="#8fbfb9"),  # Cor da paleta
        border_color="#649ea7",  # Cor da paleta
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

    # Função para enviar os dados
    def enviar(evento):
        data = {
            "nome": nome_value.value,
            "cpf": cpf_value.value,
            "email": email_value.value,
            "senha": senha_value.value,
        }

        try:
            response = requests.post(
                f"http://localhost:8080/auth/register", json=data)

            if response.status_code == 200 or response.status_code == 201:
                mensagem_api.value = "Cadastrado com sucesso!"
                mensagem_api.color = "#00FF00"  # Cor da paleta
            else:
                mensagem_api.value = response.json().get("mensagem", "Erro desconhecido.")
                mensagem_api.color = "#FF0000"  # Cor da paleta

        except requests.exceptions.RequestException as e:
            mensagem_api.value = f"Erro ao enviar dados: {e}"
            mensagem_api.color = "#eefaa8"  # Cor da paleta

        # Limpa os campos
        nome_value.value = ""
        senha_value.value = ""
        email_value.value = ""
        cpf_value.value = ""

        nome_value.update()
        senha_value.update()
        email_value.update()
        cpf_value.update()
        mensagem_api.update()

        time.sleep(1)

        mensagem_api.value = ""
        mensagem_api.update()

    # Layout da tela
    return ft.Container(
        content=ft.Column(
            [
                # Título com traço embaixo
                ft.Column(
                    [
                        ft.Text(
                            value="Registro",
                            color="#649ea7",  # Cor da paleta
                            font_family="MinhaFonte",
                            size=50,
                            weight=ft.FontWeight.BOLD,
                        ),
                        ft.Divider(height=2, thickness=2,
                                   color="#8fbfb9"),  # Traço
                    ],
                    alignment=ft.MainAxisAlignment.CENTER,
                    horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                ),
                ft.Container(height=20),  # Espaço entre o título e os campos
                nome_value,
                cpf_value,
                email_value,
                senha_value,
                ft.Container(height=10),  # Espaço entre os campos e o botão
                # Botão de Enviar
                ft.ElevatedButton(
                    text="Enviar",
                    bgcolor="#649ea7",  # Cor da paleta
                    color="#ffffff",
                    width=400,
                    height=50,
                    on_click=enviar,
                    style=ft.ButtonStyle(
                        shape=ft.RoundedRectangleBorder(radius=10),
                    ),
                ),
                ft.Container(height=10),  # Espaço entre o botão e a mensagem
                mensagem_api,
                # Espaço entre a mensagem e o botão de voltar
                ft.Container(height=20),
                # Botão de Voltar
                ft.TextButton(
                    text="Voltar",
                    on_click=on_login,
                    style=ft.ButtonStyle(
                        color="#649ea7",  # Cor da paleta
                        overlay_color="#8fbfb9",
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
        image_src=image,
        image_fit=ft.ImageFit.COVER,
    )
