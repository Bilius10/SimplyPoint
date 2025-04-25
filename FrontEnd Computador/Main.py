import flet as ft


def main(page: ft.Page):
    page.title = "Hello, World App"
    page.add(ft.Text("Hello, World!"))


ft.app(target=main, view=ft.WEB_BROWSER)