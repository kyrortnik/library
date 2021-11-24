<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="headerAnim">
    <div>
        <div>
            <div>
                <form action="frontController" method="GET">
                    <input type="hidden" name="command" value="change_Language"/>
                    <input type="hidden" name="local" value="en"/>
                    <button class="btn btn-default" type="submit">
                        EN
                    </button>
                </form>
            </div>
            <div>
                <form action="frontController" method="GET">
                    <input type="hidden" name="command" value="change_Language"/>
                    <input type="hidden" name="local" value="ru"/>
                    <button class="btn btn-default" type="submit">
                        РУ
                    </button>
                </form>
            </div>
        </div>
<!--        <c:if test="${sessionScope.role == 'user'}">-->
<!--            <div class="col-md-1" style="padding-top:10px;">-->
<!--                <form method="GET" action="frontController">-->
<!--                    <input type="hidden" name="command" value="go_To_Page"/>-->
<!--                    <input type="hidden" name="address" value="profile.jsp"/>-->
<!--                    <button >-->
<!--                        <c:out  value="updateProfile"/>-->
<!--                    </button>-->
<!--                </form>-->
<!--            </div>-->
<!--        </c:if>-->
            <div>
                <div>
                    <h4>
                        <c:out value="${sessionScope.login}"/>
                    </h4>
                </div>
                <div>
                    <form id="logout" action="frontController" method="POST">
                        <input type="hidden" name="command" value="logout"/>
                        <button form="logout" type="submit">${logout}</button>
                    </form>
                </div>
            </div>
    </div>
</div>