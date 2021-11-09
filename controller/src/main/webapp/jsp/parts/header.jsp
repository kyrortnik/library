<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div style ="text-align:center">
    <h3>Welcome</h3>
</div>

<div class="headerAnim">
    <div>
        <div>
            <div>
                <form action="frontController" method="GET">
                    <input type="hidden" name="command" value="changeLanguage"/>
                    <input type="hidden" name="local" value="en"/>
                    <button class="btn btn-default" type="submit">
                        EN
                    </button>
                </form>
            </div>
            <div>
                <form action="frontController" method="GET">
                    <input type="hidden" name="command" value="changeLanguage"/>
                    <input type="hidden" name="local" value="ru"/>
                    <button class="btn btn-default" type="submit">
                        РУ
                    </button>
                </form>
            </div>
            <!--<div>
                <form action="frontController" method="GET">
                    <input type="hidden" name="command" value="changeLanguage"/>
                    <input type="hidden" name="local" value="pl"/>
                    <button class="btn btn-default" type="submit">
                        PL
                    </button>
                </form>
            </div>-->
        </div>
        <div>
            <c:if test="${sessionScope.role == 'admin'}">
						<span>
							<c:out value="Admin"/>
						</span>
            </c:if>
            <c:if test="${sessionScope.role == 'user'}">
						<span>
							<c:out value="User"/>
						</span>
            </c:if>
        </div>
        <div class="col-md-5" style="text-align:center">
            <h1>
                <c:out value="Hello" />
            </h1>
        </div>
        <c:if test="${sessionScope.role == null}">
            <div>
                <label for="login">
                    <c:out value="login"/>:
                </label>
                <br/>
                <label for="password">
                    <c:out value="password"/>:
                </label>
            </div>
            <div>
                <div >
                    <form action="frontController" id="loginForm" method="POST">
                        <input type="hidden" name="command" value="login" />
                        <div>
                            <input type="text"  id="login" placeholder="login" name="login" value="" />
                        </div>
                        <div>
                            <input type="password"  id="password" placeholder="password" name="password" value="" />
                        </div>
                    </form>
                </div>
            </div>
            <div >
                <div >
                    <button form="loginForm"  type="submit">
                        <c:out value="signIn"/>
                    </button>
            </div>
            <div class="col-md-1">
                <%--should be empty --%>
            </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.role == 'user'}">
            <div class="col-md-1" style="padding-top:10px;">
                <form method="GET" action="frontController">
                    <input type="hidden" name="command" value="goToPage"/>
                    <input type="hidden" name="address" value="profile.jsp"/>
                    <button >
                        <c:out  value="updateProfile"/>
                    </button>
                </form>
            </div>
        </c:if>
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