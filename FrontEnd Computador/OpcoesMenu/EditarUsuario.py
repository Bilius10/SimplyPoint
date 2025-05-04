import flet as ft
import requests
import time
from datetime import datetime
from session import session


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

    # Campos de texto com tamanho ajustado
    cpf_value = ft.TextField(label="CPF", width=350, height=45,
                             border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_entrada_value = ft.TextField(label="Hora Entrada (HH:mm)", width=350,
                                      height=45, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_entrada_almoco_value = ft.TextField(label="Hora Entrada Almoço (HH:mm)", width=350,
                                             height=45, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_saida_almoco_value = ft.TextField(label="Hora Saída Almoço (HH:mm)", width=350,
                                           height=45, border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    hora_saida_value = ft.TextField(label="Hora Saída (HH:mm)", width=350, height=45,
                                    border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    salario_value = ft.TextField(label="Salário", width=350, height=45,
                                 border_color="#649ea7", text_style=ft.TextStyle(color="#000000"))
    cargo_value = ft.TextField(label="Cargo", width=350, height=45,
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
           
            def formatar_hora(valor):

                if(valor == ""):
                    return "00:00:00"
                
                return datetime.strptime(valor, "%H:%M").strftime("%H:%M:%S")

           
            data = {
                "cpf": cpf_value.value,
                "horaEntrada": formatar_hora(hora_entrada_value.value),
                "horaEntradaAlmoco": formatar_hora(hora_entrada_almoco_value.value),
                "horaSaidaAlmoco": formatar_hora(hora_saida_almoco_value.value),
                "horaSaida": formatar_hora(hora_saida_value.value),
                "salario": float(salario_value.value),
                "cargo": cargo_value.value,
            }
            
            headers = {
                "Authorization": f"Bearer {session.user_data.get('token', '')}"
            }

            response = requests.put(
                "http://localhost:8080/infoUsuario", json=data, headers=headers
            )
       
            if response.status_code == 200:
                mensagem_api.value = "Usuário atualizado com sucesso!"
                mensagem_api.color = "#00FF00"  
            else:
                mensagem_api.value = "Erro ao atualizar usuário."
                mensagem_api.color = "#FF0000"  

        except Exception as e:
            mensagem_api.value = f"Erro: {e}"
            mensagem_api.color = "#FF0000"  

        mensagem_api.update()

        time.sleep(1)

        mensagem_api.value = ""
        mensagem_api.update()


    # Botão de enviar
    botao_enviar = ft.ElevatedButton(
        text="Enviar",
        bgcolor="#649ea7",  # Cor da paleta
        color="#ffffff",
        width=350,
        height=40,
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
        width=350,
        height=40,
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
                ft.Container(height=15),  # Espaço entre o título e os campos
                cpf_value,
                hora_entrada_value,
                hora_entrada_almoco_value,
                hora_saida_almoco_value,
                hora_saida_value,
                salario_value,
                cargo_value,

                mensagem_api,

                botao_enviar,

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
