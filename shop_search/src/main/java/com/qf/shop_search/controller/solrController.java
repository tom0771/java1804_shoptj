package com.qf.shop_search.controller;

import com.qf.entity.Goods;
import com.qf.entity.SolrPage;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/solr")
public class solrController {

    @Autowired
    private SolrClient solrClient;



    @RequestMapping("/addsolr")
    @ResponseBody
    public boolean addsolr(@RequestBody Goods goods){
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("id",goods.getId());
        solrInputFields.addField("gtitle",goods.getTitle());
        solrInputFields.addField("ginfo",goods.getGinfo());
        solrInputFields.addField("gprice",goods.getPrice());
        solrInputFields.addField("gimage",goods.getGimage());

        try {
            solrClient.add(solrInputFields);
            solrClient.commit();
        return true;
    } catch (SolrServerException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


        return false;
    }

    @RequestMapping("/getAllGoods")
    @ResponseBody
    public String getallGoods( @RequestBody List<Goods> allGoods){



        for (Goods gs : allGoods) {
            SolrInputDocument solrInputFields = new SolrInputDocument();
            solrInputFields.addField("id",gs.getId());
            solrInputFields.addField("gtitle",gs.getTitle());
            solrInputFields.addField("ginfo",gs.getGinfo());
            solrInputFields.addField("gprice",gs.getPrice());
            solrInputFields.addField("gimage",gs.getGimage());

            try {
                solrClient.add(solrInputFields);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok已将数据库的数据刷新到索引库中";
    }

    @RequestMapping("/query")
    public String queryGoods(String keyword, Model model, SolrPage<Goods> solrPage){
        SolrQuery solrQuery = new SolrQuery();

        if(keyword==null || keyword.trim().equals("")){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("goods_info:" + keyword);
        }
        //开启高亮
        solrQuery.setHighlight(true);
        //设置高亮的前缀后缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //添加需要设置高亮的字段
        solrQuery.addHighlightField("gtitle");

        //设置分页展示内容
        solrQuery.setStart((solrPage.getIndexPage()-1)*solrPage.getPageCount());
        solrQuery.setRows(solrPage.getPageCount());

            QueryResponse query = null ;
            List<Goods> goodsList = new ArrayList<>();
        try {
             query = solrClient.query(solrQuery);

            //获得高亮的内容
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();


            //获得普通的结果
            SolrDocumentList results = query.getResults();

            //拿到总条数，处理总页数
            long totalCount = results.getNumFound();
            solrPage.setTotalCount((int)totalCount);
            solrPage.setTotalPage(solrPage.getTotalCount()%solrPage.getPageCount()==0?
                    solrPage.getTotalCount()/solrPage.getPageCount():
                    solrPage.getTotalCount()/solrPage.getPageCount()+1
            );

            for (SolrDocument result : results) {
                Goods goods = new Goods();

                goods.setId(Integer.parseInt( result.getFieldValue("id")+""));
                goods.setTitle(result.getFieldValue("gtitle")+"");
                goods.setGimage((String) result.getFieldValue("gimage"));
                goods.setPrice(Double.parseDouble( result.getFieldValue("gprice")+""));

                //高亮内容的处理
                if(highlighting.containsKey(goods.getId()+"")){
                    //有高亮的信息设置为高亮
                    List<String> gtitle = highlighting.get(goods.getId() + "").get("gtitle");
                    if(gtitle !=null){
                        goods.setTitle(gtitle.get(0));
                    }

                }



                goodsList.add(goods);
            }




        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        solrPage.setData(goodsList);
        model.addAttribute("page",solrPage);
        model.addAttribute("keyword",keyword);
        return "searchlist";
    }

}
