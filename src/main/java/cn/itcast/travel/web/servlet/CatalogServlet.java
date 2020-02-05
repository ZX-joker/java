package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CatalogService;
import cn.itcast.travel.service.impl.CatalogServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/catagory/*")
public class CatalogServlet extends BaseServlet {
    private CatalogService catalogService = new CatalogServiceImp();
    //查找所有的分类
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = catalogService.findAll();
        writeValue(list,response);
    }
}
