import flet as ft
import requests
from datetime import datetime
import session

def editar_usuario_page(on_menu):
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Título da página
    titulo = ft.Text(
        value="Editar Usuário",
        color="#649ea7",  # Cor da paleta
        font_family="MinhaFonte",
        size=40,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    # Campos de texto
    cpf_value = ft.TextField(label="CPF", width=400, height=50,
                             border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_entrada_value = ft.TextField(label="Hora Entrada (HH:mm)", width=400,
                                      height=50, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_entrada_almoco_value = ft.TextField(label="Hora Entrada Almoço (HH:mm)", width=400,
                                             height=50, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_saida_almoco_value = ft.TextField(label="Hora Saída Almoço (HH:mm)", width=400,
                                           height=50, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_saida_value = ft.TextField(label="Hora Saída (HH:mm)", width=400, height=50,
                                    border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    salario_value = ft.TextField(label="Salário", width=400, height=50,
                                 border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    cargo_value = ft.TextField(label="Cargo", width=400, height=50,
                               border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))

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
        try:
            data = {
                "cpf": cpf_value.value,
                "horaEntrada": datetime.strptime(hora_entrada_value.value, "%H:%M").isoformat(),
                "horaEntradaAlmoco": datetime.strptime(hora_entrada_almoco_value.value, "%H:%M").isoformat(),
                "horaSaidaAlmoco": datetime.strptime(hora_saida_almoco_value.value, "%H:%M").isoformat(),
                "horaSaida": datetime.strptime(hora_saida_value.value, "%H:%M").isoformat(),
                "salario": float(salario_value.value),
                "cargo": cargo_value.value,
            }

            headers = {"Authorization": f"Bearer {session.user_data['token']}"}

            response = requests.put(
                "http://localhost:8080/infoUsuario", json=data, headers=headers)

            if response.status_code == 200:
                mensagem_api.value = "Usuário atualizado com sucesso!"
                mensagem_api.color = "#00FF00"  # Verde
            else:
                mensagem_api.value = "Erro ao atualizar usuário."
                mensagem_api.color = "#FF0000"  # Vermelho

        except Exception as e:
            mensagem_api.value = f"Erro: {e}"
            mensagem_api.color = "#FF0000"  # Vermelho

        mensagem_api.update()

    # Botão de enviar
    botao_enviar = ft.ElevatedButton(
        text="Enviar",
        bgcolor="#649ea7",  # Cor da paleta
        color="#ffffff",
        width=400,
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
        width=400,
        height=50,
        on_click=on_menu,
        style=ft.ButtonStyle(
            shape=ft.RoundedRectangleBorder(radius=10),
        ),
    )

    # Layout da tela
    return ft.Container(
        content=ft.Column(
            [
                titulo,
                ft.Container(height=20),  # Espaço entre o título e os campos
                cpf_value,
                hora_entrada_value,
                hora_entrada_almoco_value,
                hora_saida_almoco_value,
                hora_saida_value,
                salario_value,
                cargo_value,
                ft.Container(height=10),  # Espaço entre os campos e o botão
                botao_enviar,
                ft.Container(height=10),  # Espaço entre o botão e a mensagem
                mensagem_api,
                # Espaço entre a mensagem e o botão de voltar
                ft.Container(height=20),
                botao_voltar,
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
