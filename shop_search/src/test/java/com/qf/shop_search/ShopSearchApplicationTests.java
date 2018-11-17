package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

	@Autowired
	private SolrClient solrClient;

	/*@Reference
	private IGoodsService goodsService;

	@Test
	public  void addGoods(){

		List<Goods> allGoods = goodsService.getAllGoods();

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
	}*/


	/*@Test
	public  void addsolr(){
			//添加数据到索引库
		for (int i=1;i<10;i++){
			SolrInputDocument solrInputFields = new SolrInputDocument();
			solrInputFields.addField("id",i);
			solrInputFields.addField("gtitle","华为荣耀"+i);
			solrInputFields.addField("ginfo","你值得拥有的荣耀"+i);
			solrInputFields.addField("gprice",1800*i);
			solrInputFields.addField("gimage","http:///www.baidu.com"+i);

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


	}*/

	/*@Test
	public  void delete(){

		try {
			//根据id删除索引数据
			solrClient.deleteById("10");
			//根据查询结果删除数据
			*//*solrClient.deleteByQuery("gtitle:荣耀");
			solrClient.commit();*//*
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
	/*@Test
	public  void querySolr(){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("gtitle:荣耀");
		try {

			QueryResponse query = solrClient.query(solrQuery);
			SolrDocumentList results = query.getResults();
			for (SolrDocument result : results) {
				String id = (String) result.getFieldValue("id");
				String gtitle = (String)result.getFieldValue("gtitle");
				String ginfo = (String)result.getFieldValue("ginfo");
				float gprice = (float)result.getFieldValue("gprice");
				String gimage = (String)result.getFieldValue("gimage");

				System.out.println(id);
				System.out.println(gtitle);
				System.out.println(ginfo);
				System.out.println(gprice);
				System.out.println(gimage);
				System.out.println("++++++++++++++++++++++++++++++++");

			}


		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}*/

}
