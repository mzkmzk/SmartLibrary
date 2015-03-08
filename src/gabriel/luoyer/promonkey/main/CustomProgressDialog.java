private void huiyuanLogin(HttpServletRequest request,
            HttpServletResponse response) {
        //从前台表单获取用户名密码
        String accountname = request.getParameter("accountname");
        String password = request.getParameter("password");


        String filter = MessageFormat.format(
                "where accountname=''{0}'' and password=''{1}''", accountname,
                password);

        //从huiyuan表中根据用户名密码查询出对应的账号
        Huiyuan huiyuan = (Huiyuan) DALBase.load("huiyuan", filter);
        String errorurl=request.getParameter("errorurl");
        String forwardurl=request.getParameter("forwardurl");


        if (huiyuan != null && huiyuan.getPassword().equals(password)) {
            //当登陆成功时
            try {

                huiyuan.setLogtimes(huiyuan.getLogtimes() + 1);
                DALBase.update(huiyuan);
                //往session作用域放会员信息
                request.getSession().setAttribute("huiyuan", huiyuan);
                if (forwardurl != "")
                    response.sendRedirect(SystemParam.getSiteRoot()
                            + forwardurl);

                else {

                    response.sendRedirect(SystemParam.getSiteRoot() + "/e/huiyuan/accounintfo.jsp");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {

            dispatchParams(request, response);
            request.setAttribute("message", "系统账户和密码不匹配");
            try {

                request.getRequestDispatcher(errorurl).forward(request, response);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
