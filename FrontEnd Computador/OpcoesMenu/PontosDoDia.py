import requests
import flet as ft
import time
from session import session

def pontos_do_dia_page(on_menu):
    # Verifique se o caminho da imagem está correto
    image = r"C:\Users\João Vitor\IdeaProjects\SymplePoint\FrontEnd Celular\Imagens\Fundo.png"

    # Título da página
    titulo = ft.Text(
        value="Pontos do Dia",
        color="#649ea7",  
        font_family="MinhaFonte",
        size=40,
        weight=ft.FontWeight.BOLD,
        text_align=ft.TextAlign.CENTER,
    )

    # Função para buscar os dados da API
    def buscar_dados():
        try:
            headers = {"Authorization": f"Bearer {session.user_data.get("token")}"}

            response = requests.get("http://localhost:8080/ponto/pontoDoDia",headers=headers)

            if response.status_code == 200:
                return response.json()  # Retorna os dados do JSON
            else:
                print(f"Erro ao buscar dados: {response.status_code}")
                return []
        except requests.exceptions.RequestException as e:
            print(f"Erro de conexão: {e}")
            return []

    # Processar os dados recebidos da API
    dados_api = buscar_dados()
    linhas_tabela = []

    for dado in dados_api:
        pontos = dado.get("pontosBatidos", [])
        # Preenche com valores vazios caso não tenha todos os pontos
        while len(pontos) < 5:
            pontos.append("-")
        linhas_tabela.append(
            ft.DataRow(
                cells=[
                    ft.DataCell(ft.Text(dado.get("nomeFuncionario", ""),
                                text_align=ft.TextAlign.CENTER, color="#000000")),
                    ft.DataCell(
                        ft.Text(pontos[0], text_align=ft.TextAlign.CENTER, color="#000000")),
                    ft.DataCell(
                        ft.Text(pontos[1], text_align=ft.TextAlign.CENTER, color="#000000")),
                    ft.DataCell(
                        ft.Text(pontos[2], text_align=ft.TextAlign.CENTER, color="#000000")),
                    ft.DataCell(
                        ft.Text(pontos[3], text_align=ft.TextAlign.CENTER, color="#000000")),
                ]
            )
        )

    # Tabela estruturada
    tabela = ft.DataTable(
        columns=[
            ft.DataColumn(ft.Text("Funcionário", weight=ft.FontWeight.BOLD,
                          color="#649ea7", text_align=ft.TextAlign.CENTER)),
            ft.DataColumn(ft.Text("Entrada", weight=ft.FontWeight.BOLD,
                          color="#649ea7", text_align=ft.TextAlign.CENTER)),
            ft.DataColumn(ft.Text("Entrada Almoço", weight=ft.FontWeight.BOLD,
                          color="#649ea7", text_align=ft.TextAlign.CENTER)),
            ft.DataColumn(ft.Text("Saída Almoço", weight=ft.FontWeight.BOLD,
                          color="#649ea7", text_align=ft.TextAlign.CENTER)),
            ft.DataColumn(ft.Text("Saída", weight=ft.FontWeight.BOLD,
                          color="#649ea7", text_align=ft.TextAlign.CENTER)),
        ],
        rows=linhas_tabela,
        # Bordas azuis claras
        border=ft.border.all(1, ft.colors.LIGHT_BLUE_200),
        horizontal_lines=True,
        vertical_lines=True,
        bgcolor=None,  # Remove qualquer fundo cinza da tabela
    )

    # Botão de voltar
    botao_voltar = ft.ElevatedButton(
        text="Voltar",
        bgcolor="#649ea7",  # Cor da paleta
        color="#ffffff",
        width=200,
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
                # Título no topo
                titulo,
                ft.Divider(height=2, thickness=2, color="#8fbfb9"),  # Traço
                ft.Container(height=20),  # Espaço entre o título e a tabela
                tabela,
                ft.Container(height=20),  # Espaço entre a tabela e o botão
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
        image_src=image,  # Fundo da tela
        image_fit=ft.ImageFit.COVER,
    )
