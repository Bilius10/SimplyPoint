import flet as ft
from TelaPonto import telaPonto_page
from Register import register_page as registro_page
from Login import login_page

def main(page: ft.Page):
    page.title = "SymplePoint"
    page.bgcolor = "#000000"
    page.vertical_alignment = ft.MainAxisAlignment.CENTER
    page.horizontal_alignment = ft.CrossAxisAlignment.CENTER

    def on_telaPonto(event):
        page.clean()
        page.add(telaPonto_page(on_login))

    def on_registro(event):
        page.clean()
        page.add(registro_page(on_login))

    def on_login(event=None):
        page.clean()
        page.add(login_page(on_registro, on_telaPonto))

    on_login()
    
ft.app(target=main)
