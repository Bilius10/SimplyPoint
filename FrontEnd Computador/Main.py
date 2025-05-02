import flet as ft
from Login.Registro.Login import login_page
from Login.Registro.Register import register_page
from Menu import menu_page
from OpcoesMenu.PontosDoDia import pontos_do_dia_page
from OpcoesMenu.EditarUsuario import editar_usuario_page
from OpcoesMenu.DadosMensais import dados_mensais_page

def main(page: ft.Page):
    page.title = "SymplePoint"
    page.bgcolor = "#000000"
    page.vertical_alignment = ft.MainAxisAlignment.CENTER
    page.horizontal_alignment = ft.CrossAxisAlignment.CENTER
    
    def on_editar_usuario(event):
        page.clean()
        page.add(editar_usuario_page(on_menu))

    def on_registro(event):
        page.clean()
        page.add(register_page(on_login))

    def on_login(event=None):
        page.clean()
        page.add(login_page(on_registro, on_menu))

    def on_menu(event):
        page.clean()
        page.add(menu_page(on_login, on_pontos_do_dia, on_editar_usuario, on_dados_mensais))

    def on_pontos_do_dia(event):
        page.clean()
        page.add(pontos_do_dia_page(on_menu))
    
    def on_dados_mensais(event):
        page.clean()
        page.add(dados_mensais_page(on_menu))

    on_login()
    
ft.app(target=main)