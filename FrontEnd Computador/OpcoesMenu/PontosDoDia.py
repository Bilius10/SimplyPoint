import requests
import flet as ft
import flet_map as map
import time
from session import session


def pontos_do_dia_page(on_menu):
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    def get_horario(index, dados):
        horarios = dados.get("horarios", [])
        return horarios[index] if index < len(horarios) else "00:00"

    def enviar(evento):
        try:
            headers = {
                "Authorization": f"Bearer {session.user_data.get('token', '')}"
            }

            response = requests.get(
                f"http://localhost:8080/ponto/pontoDoDia/cpf/{cpf_value.value}", headers=headers
            )

            if response.status_code == 200:
                dados = response.json()

                mensagem_api.value = "Usuário encontrado"
                mensagem_api.color = "#00FF00"

                nome_usuario.value = f"Nome: {dados.get('nomeUsuario', 'Desconhecido')}"
                hora_entrada.value = f"Hora de Entrada: {get_horario(0, dados)}"
                hora_almoco.value = f"Hora do Almoço: {get_horario(1, dados)}"
                hora_volta.value = f"Hora da Volta: {get_horario(2, dados)}"
                hora_saida.value = f"Hora de Saída: {get_horario(3, dados)}"

                # Atualiza o mapa com os marcadores
                latitudes = dados.get("latitudes", [])
                longitudes = dados.get("longitudes", [])
                horarios = dados.get("horarios", [])
                marker_layer_ref.current.markers.clear()

                for i, (lat, lon) in enumerate(zip(latitudes, longitudes)):
                    marker_layer_ref.current.markers.append(
                        map.Marker(
                            content=ft.Column(
                                [
                                    ft.Icon(ft.Icons.LOCATION_ON, color=ft.Colors.RED),
                                    ft.Text(f"{get_horario(i, dados)}", size=7, color="#000000"),
                                ]
                            ),
                            coordinates=map.MapLatitudeLongitude(lat, lon),
                        )
                    )
                mapa.update()

            else:
                mensagem_api.value = response.json().get("mensagem", "Erro desconhecido.")
                mensagem_api.color = "#FF0000"

        except requests.exceptions.RequestException as e:
            mensagem_api.value = f"Erro ao enviar dados: {e}"
            mensagem_api.color = "#eefaa8"

        cpf_value.value = ""
        cpf_value.update()
        mensagem_api.update()
        nome_usuario.update()
        hora_entrada.update()
        hora_almoco.update()
        hora_volta.update()
        hora_saida.update()

        time.sleep(1)
        mensagem_api.value = ""
        mensagem_api.update()

    # Campo de CPF
    cpf_value = ft.TextField(
        label="CPF",
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
    nome_usuario = ft.Text(
        value=f"Nome:",
        color="#8fbfb9",
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_entrada = ft.Text(
        value=f"Hora de Entrada: 00:00",
        color="#8fbfb9",
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_almoco = ft.Text(
        value=f"Hora do Almoço: 00:00",
        color="#8fbfb9",
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_volta = ft.Text(
        value=f"Hora da Volta: 00:00",
        color="#8fbfb9",
        size=20,
        weight=ft.FontWeight.BOLD,
    )
    hora_saida = ft.Text(
        value=f"Hora de Saída: 00:00",
        color="#8fbfb9",
        size=20,
        weight=ft.FontWeight.BOLD,
    )

    # Primeiro container
    container_1 = ft.Container(
        content=ft.Column(
            [
                ft.Column(
                    [
                        ft.Text(
                            value="Dados do Dia",
                            color="#649ea7",
                            font_family="MinhaFonte",
                            size=50,
                            weight=ft.FontWeight.BOLD,
                        ),
                        ft.Divider(height=2, thickness=2, color="#8fbfb9"),
                    ],
                    alignment=ft.MainAxisAlignment.CENTER,
                    horizontal_alignment=ft.CrossAxisAlignment.CENTER,
                ),
                cpf_value,
                ft.Container(height=20),
                nome_usuario,
                hora_entrada,
                hora_almoco,
                hora_volta,
                hora_saida,
                ft.Container(height=20),
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
                ft.Container(height=10),
                mensagem_api,
                ft.Container(height=20),
                ft.TextButton(
                    text="Voltar",
                    on_click=on_menu,
                    style=ft.ButtonStyle(
                        color="#649ea7",
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

    # Mapa no segundo container
    marker_layer_ref = ft.Ref[map.MarkerLayer]()
    mapa = map.Map(
        expand=True,
        initial_center=map.MapLatitudeLongitude(-15.7942, -47.8822),  # Centro inicial (Brasília)
        initial_zoom=5,
        min_zoom=5,  # Define o nível mínimo de zoom
        layers=[
            map.TileLayer(
                url_template="https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            ),
            map.MarkerLayer(ref=marker_layer_ref, markers=[]),
        ],
    )

    container_2 = ft.Container(
        content=mapa,
        width=500,
        height=700,
        padding=20,
        border_radius=ft.border_radius.all(50),  # Bordas arredondadas
        alignment=ft.alignment.center,
        image_src=image,
        image_fit=ft.ImageFit.COVER,
    )

    # Layout principal com os dois containers lado a lado
    return ft.Row(
        [
            container_1,
            container_2,
        ],
        alignment=ft.MainAxisAlignment.CENTER,
        spacing=20,
    )