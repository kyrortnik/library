<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>New Book Form</title>
<!--    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>-->
<!--    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">-->
<!--    <style>-->
<!--      html, body {-->
<!--      height: 100%;-->
<!--      }-->
<!--      body, input, select {-->
<!--      padding: 0;-->
<!--      margin: 0;-->
<!--      outline: none;-->
<!--      font-family: Roboto, Arial, sans-serif;-->
<!--      font-size: 16px;-->
<!--      color: #eee;-->
<!--      }-->
<!--      h1, h3 {-->
<!--      font-weight: 400;-->
<!--      }-->
<!--      h1 {-->
<!--      font-size: 32px;-->
<!--      }-->
<!--      h3 {-->
<!--      color: #1c87c9;-->
<!--      }-->
<!--      .main-block, .info {-->
<!--      display: flex;-->
<!--      flex-direction: column;-->
<!--      }-->
<!--      .main-block {-->
<!--      justify-content: center;-->
<!--      align-items: center;-->
<!--      width: 100%;-->
<!--      min-height: 100%;-->
<!--      background: url("/uploads/media/default/0001/01/e7a97bd9b2d25886cc7b9115de83b6b28b73b90b.jpeg") no-repeat center;-->
<!--      background-size: cover;-->
<!--      }-->
<!--      form {-->
<!--      width: 80%;-->
<!--      padding: 25px;-->
<!--      margin-bottom: 20px;-->
<!--      background: rgba(0, 0, 0, 0.9);-->
<!--      }-->
<!--      input, select {-->
<!--      padding: 5px;-->
<!--      margin-bottom: 20px;-->
<!--      background: transparent;-->
<!--      border: none;-->
<!--      border-bottom: 1px solid #eee;-->
<!--      }-->
<!--      input::placeholder {-->
<!--      color: #eee;-->
<!--      }-->
<!--      option {-->
<!--      background: black;-->
<!--      border: none;-->
<!--      }-->
<!--      .metod {-->
<!--      display: flex;-->
<!--      }-->
<!--      input[type=radio] {-->
<!--      display: none;-->
<!--      }-->
<!--      label.radio {-->
<!--      position: relative;-->
<!--      display: inline-block;-->
<!--      margin-right: 20px;-->
<!--      text-indent: 32px;-->
<!--      cursor: pointer;-->
<!--      }-->
<!--      label.radio:before {-->
<!--      content: "";-->
<!--      position: absolute;-->
<!--      top: -1px;-->
<!--      left: 0;-->
<!--      width: 17px;-->
<!--      height: 17px;-->
<!--      border-radius: 50%;-->
<!--      border: 2px solid #1c87c9;-->
<!--      }-->
<!--      label.radio:after {-->
<!--      content: "";-->
<!--      position: absolute;-->
<!--      width: 8px;-->
<!--      height: 4px;-->
<!--      top: 5px;-->
<!--      left: 5px;-->
<!--      border-bottom: 3px solid #1c87c9;-->
<!--      border-left: 3px solid #1c87c9;-->
<!--      transform: rotate(-45deg);-->
<!--      opacity: 0;-->
<!--      }-->
<!--      input[type=radio]:checked + label:after {-->
<!--      opacity: 1;-->
<!--      }-->
<!--      button {-->
<!--      display: block;-->
<!--      width: 200px;-->
<!--      padding: 10px;-->
<!--      margin: 20px auto 0;-->
<!--      border: none;-->
<!--      border-radius: 5px;-->
<!--      background: #1c87c9;-->
<!--      font-size: 14px;-->
<!--      font-weight: 600;-->
<!--      color: #fff;-->
<!--      }-->
<!--      button:hover {-->
<!--      background: #095484;-->
<!--      }-->
<!--      @media (min-width: 568px) {-->
<!--      .info {-->
<!--      flex-flow: row wrap;-->
<!--      justify-content: space-between;-->
<!--      }-->
<!--      input {-->
<!--      width: 46%;-->
<!--      }-->
<!--      input.fname {-->
<!--      width: 100%;-->
<!--      }-->
<!--      select {-->
<!--      width: 48%;-->
<!--      }-->
<!--      }-->
<!--    </style>-->
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="main-block">
    <h1>New Book</h1>
    <form id="createBook" action="frontController" method="POST">
        <div class="info">
            <input type="hidden" name="command" value="createBook"/>
            <input class="fname" type="text" name="title" placeholder="Book title"/>
            <input type="text" name="author" placeholder="Author"/>
            <input type="text" name="publisher" placeholder="publisher"/>
            <input type="text" name="publishingYear" placeholder="publishing Year"/>
            <input type="text" name="isHardCover" placeholder="is hard cover"/>
            <input type="text" name="numberOfPages" placeholder="number of pages"/>
            <input type="text" name="genre" placeholder="Genre"/>
            <input type="text" name="description" placeholder="Description"/>
        </div>
        <button form="createBook" class="button">Submit</button>
    </form>
</div>
</body>
</html>