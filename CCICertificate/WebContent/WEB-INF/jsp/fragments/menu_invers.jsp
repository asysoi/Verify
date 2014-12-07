<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">

				
   <div class="container-fluid">
      
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">БелТПП</a>
        </div>

		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
			
				<form class="navbar-form navbar-right">
            		<input type="text" class="form-control" placeholder="Search...">
          		</form>

			
				<li><a href="main.do">Главная</a></li>
								
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Клиенты<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="members.do">Члены БелТПП</a></li>
						<li><a href="clients.do">Контрагенты</a></li>
						<li class="divider"></li>
						<li><a href="#">Импорт данных</a></li>
					</ul>
				</li>
				
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ВЭС<b class="caret"></b></a>
					<ul class="dropdown-menu">
					    <li><a href="#">Все Мероприятия</a></li>
                        <li><a href="#">Деловые советы</a></li>					
						<li><a href="#">Форумы</a></li>					
						<li><a href="#">Выствыки</a></li>
						<li><a href="#">Ярмарки</a></li>
						<li class="divider"></li>
						<li><a href="#">Импорт данных</a></li>

					</ul>
				</li>
				

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Кадры<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Сотрудники</a></li>					
						<li><a href="#">Приказы</a></li>
						<li><a href="#">Документы</a></li>
						<li class="divider"></li>
						<li><a href="#">Импорт данных</a></li>

					</ul>
				</li>

				<security:authorize ifAnyGranted="ROLE_EXPERT">				
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Сертификаты<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="certs.do">Список Сертификатов</a></li>
						<li><a href="check.do">Верификация</a></li>
						<li><a href="#">Акты экспертиз</a></li>
						<li class="divider"></li>
						<li><a href="#">Импорт сертификатов</a></li>

					</ul>
				</li>
			    </security:authorize>				

                
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Справочники<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="cert.do">Телефонный</a></li>
						<li><a href="certs.do">Арендные помещения</a></li>
					</ul>
				</li>

				<security:authorize ifAnyGranted="ROLE_ACCOUNT">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Логистика<b class="caret"></b></a>
					<ul class="dropdown-menu">
                        <li><a href="purchases.do">Сделки</a></li>
						<li><a href="#">Договора</a></li>					
					</ul>
				</li>
			    </security:authorize>
				
				
				<li><a href="logout.do">
				<security:authorize access="isAuthenticated()">
    			Выйти <security:authentication property="principal.username" /> 
				</security:authorize>
				</a></li>
				<li><a href="help.do">Помощь</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
				
      </div>
    </div>
 
