  <%@ page language="java" contentType="text/html; charset=UTF-8"
           pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="top_nav">
        <div class="nav_menu">
            <nav>
                <div class="nav toggle">
                    <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <li class="">
                        <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <img src="<c:url value="/static/images/img.jpeg"/>" alt="userName Img"/>${userInfo.name}
                        <span class=" fa fa-angle-down"></span>
                        </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <li><a href="javascript:;"> Profile</a></li>
                        <li>
                                <a href="javascript:;">
                            <%--    <span class="badge bg-red pull-right">50%</span>--%>
                                <span>Settings</span>
                                </a>
                        </li>
                        <li><a href="javascript:;">Help</a></li>
                        <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i>Log Out</a></li>
                    </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>