import flet as ft
from session import session


def menu_page(on_login, on_pontos_do_dia, on_editar_usuario):
    logo_image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Logo.png"
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Mensagem de boas-vindas
    mensagem_boas_vindas = ft.Text(
        value=f"Seja bem-vindo, {session.user_data.get('nome', 'Usuário')}!",
        color="#649ea7",  # Cor da paleta
        font_family="MinhaFonte",
        size=20,  # Tamanho reduzido
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    # Botões de opções
    botoes_opcoes = ft.Column(
        [
            ft.ElevatedButton(
                text="Pontos do Dia",
                bgcolor="#649ea7",  # Cor da paleta
                color="#ffffff",
                width=300,
                height=50,
                on_click=on_pontos_do_dia,
                style=ft.ButtonStyle(
                    shape=ft.RoundedRectangleBorder(radius=10),
                ),
            ),
            ft.ElevatedButton(
                text="Editar Funcionario",
                bgcolor="#649ea7",  # Cor da paleta
                color="#ffffff",
                width=300,
                height=50,
                on_click=on_editar_usuario,
                style=ft.ButtonStyle(
                    shape=ft.RoundedRectangleBorder(radius=10),
                ),
            ),
            ft.ElevatedButton(
                text="Visualizar Funcionários",
                bgcolor="#649ea7",  # Cor da paleta
                color="#ffffff",
                width=300,
                height=50,
                style=ft.ButtonStyle(
                    shape=ft.RoundedRectangleBorder(radius=10),
                ),
            ),
            ft.ElevatedButton(
                text="Deslogar",
                bgcolor="#FF0000",  # Cor de destaque para deslogar
                color="#ffffff",
                width=300,
                height=50,
                on_click=on_login,
                style=ft.ButtonStyle(
                    shape=ft.RoundedRectangleBorder(radius=10),
                ),
            ),
        ],
        alignment=ft.MainAxisAlignment.CENTER,
        horizontal_alignment=ft.CrossAxisAlignment.CENTER,
        spacing=15,
    )

    # Layout da tela
    return ft.Container(
        content=ft.Column(
            [
                # Logo no topo (aumentada)
                ft.Container(
                    content=ft.Image(
                        src=logo_image,
                        width=200,  # Aumentado
                        height=200,  # Aumentado
                        fit=ft.ImageFit.CONTAIN,
                    ),
                    alignment=ft.alignment.center,
                    margin=ft.margin.only(bottom=20),
                ),
                mensagem_boas_vindas,
                ft.Container(height=20),  # Espaço entre a mensagem e os botões
                botoes_opcoes,
            ],
            alignment=ft.MainAxisAlignment.CENTER,
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
            spacing=20,
        ),
        width=500,
        height=700,
        padding=20,
        border_radius=ft.border_radius.all(50),
        alignment=ft.alignment.center,
        image_src=image,  # Fundo adicionado
        image_fit=ft.ImageFit.COVER,
    )
