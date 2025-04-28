import flet as ft
import requests
import time
from session import session


def telaPonto_page(on_login):


    header = {"Authorization": "Bearer " + session.user_data.get("token")}

    response = requests.get(
        f"http://localhost:8080/ponto/pontoDoDia/{session.user_data.get("idUsuario")}", headers=header
    )
    horarios = response.json()
  
    def get_horario(index, horarios):
        return horarios[index].get("horaDoPonto", "00:00") if index < len(horarios) else "00:00"



    def bater_ponto(evento):
        try:
            data = {
                "idUsuario": session.user_data.get("idUsuario"),
                "email": session.user_data.get("email")
            }

            header = {"Authorization": "Bearer " +
                      session.user_data.get("token")}

            response = requests.post(
                f"http://localhost:8080/ponto/baterPonto", json=data, headers=header)

            if response.status_code == 200:
                mensagem_api.value = "Ponto batido com sucesso!"
                mensagem_api.color = "#00FF00"
            else:
                mensagem_api.value = response.json().get("mensagem", "Erro ao bater ponto.")
                mensagem_api.color = "#eefaa8"

        except requests.exceptions.RequestException as e:
            mensagem_api.value = f"Erro ao enviar dados: {e}"
            mensagem_api.color = "#eefaa8"

        mensagem_api.update()

    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"
    logo_image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Logo.png"

    # Textos para horários
    hora_entrada = ft.Text(
        value=f"Hora de Entrada: {get_horario(0, horarios)}",
        color="#8fbfb9",  # Cor da paleta
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_almoco = ft.Text(
        value=f"Hora do Almoço: {get_horario(1, horarios)}",
        color="#8fbfb9",  # Cor da paleta
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_volta = ft.Text(
        value=f"Hora da Volta: {get_horario(2, horarios)}",
        color="#8fbfb9",  # Cor da paleta
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_saida = ft.Text(
        value=f"Hora de Saída: {get_horario(3, horarios)}",
        color="#8fbfb9",  # Cor da paleta
        size=20,
        weight=ft.FontWeight.BOLD,
    )

    # Mensagem de resposta
    mensagem_api = ft.Text(
        "",
        color="#bddb88",  # Cor da paleta
        font_family="MinhaFonte",
        size=16,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    return ft.Container(
        content=ft.Column(
            [
                # Logo do app
                ft.Container(
                    content=ft.Image(
                        src=logo_image,
                        width=200,  # Aumentado
                        height=200,  # Aumentado
                        fit=ft.ImageFit.CONTAIN,
                    ),
                    alignment=ft.alignment.center,
                    margin=ft.margin.only(bottom=10),
                ),
                # Texto de boas-vindas
                ft.Text(
                    value=f"Seja bem-vindo, {session.user_data.get('nome', 'Usuário')}!",
                    color="#649ea7",  # Cor da paleta
                    font_family="MinhaFonte",
                    size=20,
                    weight=ft.FontWeight.BOLD,
                    text_align=ft.TextAlign.CENTER,
                ),
                ft.Container(height=20),  # Espaço entre o texto e os horários
                # Textos de horários
                hora_entrada,
                hora_almoco,
                hora_volta,
                hora_saida,
                ft.Container(height=20),  # Espaço entre os textos e a mensagem
                # Mensagem de resposta
                mensagem_api,
                ft.Container(height=20),  # Espaço entre a mensagem e o botão
                # Botão redondo para bater ponto (aumentado)
                ft.FloatingActionButton(
                    icon=ft.icons.CHECK,
                    bgcolor="#649ea7",  # Cor da paleta
                    width=80,  # Aumentado
                    height=80,  # Aumentado
                    on_click=bater_ponto,
                ),
                # Espaço entre o botão e o botão de voltar
                ft.Container(height=20),
                # Botão de voltar ao login
                ft.TextButton(
                    text="Voltar ao Login",
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
        height=800,
        padding=20,
        border_radius=ft.border_radius.all(50),
        alignment=ft.alignment.center,
        image_src=image,
        image_fit=ft.ImageFit.COVER,
    )