<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>


<div style="background-image: url(footer-background-011.jpg)">
    <div style="display: flex; justify-content : center">
        <form action="frontController" method="GET">
            <input name="command" type="hidden" value="change_Language"/>
            <input name="local" type="hidden" value="en"/>
            <button class="btn btn-lg btn-primary" type="submit">
                EN
            </button>
        </form>
        <form action="frontController" method="GET">
            <input name="command" type="hidden" value="change_Language"/>
            <input name="local" type="hidden" value="ru"/>
            <button class="btn btn-lg btn-primary" type="submit">
                РУ
            </button>
        </form>
    </div>
    <div style="color: white; text-align: center">
        ${sessionScope.user}
    </div>
    <div style="display: flex; justify-content :end ">
        <form action="frontController" id="logout" method="POST">
            <input name="command" type="hidden" value="logout"/>
            <button class="btn btn-lg btn-primary" form="logout" type="submit">${logout}</button>
        </form>
    </div>
</div>
</div>