package com.kingtake.solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.kingtake.common.constant.SrmipConstants;

public class SolrOperate {
	
	public static void main(String[] args) throws TikaException, IOException, SAXException {
		//long start = System.currentTimeMillis();
		/*Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "111");
		doc1.addField("table", "TOPrivateMemoEntity");
		doc1.addField("memo_content", "今天买了一支笔");
		doc1.addField("des_content", "明天放假");
		docs.add(doc1);
		
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "222");
		doc2.addField("table", "TOPrivateMemoEntity");
		doc2.addField("memo_content", "今天放假");
		doc2.addField("des_content", "明天去游泳");
		docs.add(doc2);
		
		SolrInputDocument doc3 = new SolrInputDocument();
		doc3.addField("id", "333");
		doc3.addField("table", "TOPrivateMemoEntity");
		doc3.addField("memo_content", "今天去游泳了");
		doc3.addField("des_content", "什么也没干");
		docs.add(doc3);*/
		
		//addIndex(docs);
		//query(null);
		//uploadFile();
		/*File file = new File("C:\\Users\\think\\Desktop\\solr5.2.1部署文档.doc");
		try {
			addFileIndex(file);
		} catch (IOException | SolrServerException e) {
			e.printStackTrace();
		}
		
		System.out.println(System.currentTimeMillis() - start + "ms");*/
		deleteAllIndex();
	}
	
	
	/**
	 * 添加文件索引
	 * @param fileList
	 * @param uploadParent
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws TikaException
	 * @throws SAXException 
	 */
	public static void addFilesIndex(List<Map<String, Object>> fileList, String uploadParent){
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
    	for(int i = 0; i < fileList.size(); i++){
    		SolrInputDocument doc = new SolrInputDocument();
    		Map<String, Object> map = fileList.get(i);
    		
    		String path = map.get("PATH").toString();
    		File file = new File(uploadParent + File.separator +path);
    		
    		if(file.exists()){
        		try {
					doc.addField("id", map.get("ID"));
					doc.addField("title", map.get("TITLE"));
					doc.addField("path", map.get("PATH"));
					doc.addField("extend", map.get("EXTEND"));
					
					Parser parser = new AutoDetectParser();
                    int writeLimit = getWriteLimit();
                    BodyContentHandler handler = new BodyContentHandler(writeLimit);
					Metadata metadata = new Metadata();
					FileInputStream inputStream = new FileInputStream(file);
					ParseContext pcontext = new ParseContext();
					
					parser.parse(inputStream, handler, metadata, pcontext);
					doc.addField("content",handler.toString());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TikaException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
        		
        		docs.add(doc);
    		}
    	}
    	
    	if(docs.size() > 0){
    		saveOrUpdateIndex(docs);
    	}
	}
	 /**
   * 添加文件索引（不包括文件内容的索引）
   * @author zhangls
   * @since 2016-01-26
   * @param fileList
   * @param uploadParent
   * @throws FileNotFoundException
   * @throws IOException
   * @throws TikaException
   * @throws SAXException 
   */
  public static void addFilesIndexNoContent(List<Map<String, Object>> fileList, String uploadParent){
    List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
    
      for(int i = 0; i < fileList.size(); i++){
        SolrInputDocument doc = new SolrInputDocument();
        Map<String, Object> map = fileList.get(i);
        
        doc.addField("id", map.get("ID"));
        doc.addField("title", map.get("TITLE"));
        doc.addField("path", map.get("PATH"));
        doc.addField("extend", map.get("EXTEND"));
            
        docs.add(doc);
      }
      
      if(docs.size() > 0){
        saveOrUpdateIndex(docs);
      }
  }
    /**
     * 获取读取字符限制
     * 
     * @return
     */
    private static int getWriteLimit() {
        int limit = 100000000;
        try {
            ResourceBundle bundle = java.util.ResourceBundle.getBundle("solr");
            String writeLimit = bundle.getString("writeLimit");
            if (StringUtils.isNotEmpty(writeLimit)) {
                limit = Integer.parseInt(writeLimit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return limit;
    }
	
	/**
	 * 添加索引
	 */
	public static void saveOrUpdateIndex(Collection<SolrInputDocument> docs){
		try {
			SolrClient client = new ConcurrentUpdateSolrClient(SrmipConstants.SOLR_URL, 10, 2);
			client.add(docs);
			client.commit();
			client.close();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除所有索引
	 */
	public static void deleteAllIndex(){
		try {
			SolrClient client = new ConcurrentUpdateSolrClient(SrmipConstants.SOLR_URL, 10, 2);
			client.deleteByQuery("*:*");
			client.commit();
			client.close();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据id删除索引
	 */
	public static void deleteIndexByIds(List<String> ids){
		try {
			SolrClient client = new ConcurrentUpdateSolrClient(SrmipConstants.SOLR_URL, 10, 2);
			client.deleteById(ids);
			client.commit();
			client.close();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据关键字进行搜索
	 * @param value
	 */
	public static QueryResponse query(String value, int start){
		try {
			SolrClient client = new HttpSolrClient(SrmipConstants.SOLR_URL);
			SolrQuery query = new SolrQuery();
			if(StringUtil.isEmpty(value)){
				query.set("q", "*:*");
			}else{
				query.set("q", value.trim());
				query.setHighlight(true);
				query.setParam("hl.fl", "title content");
				query.setHighlightSimplePre("<font color=\"red\">");
				query.setHighlightSimplePost("</font>");
			}
			query.setStart(start);
			query.setRows(SrmipConstants.PAGESIZE);
			QueryResponse response = client.query(query);
			client.close();
			return response;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void uploadFile(){
		String filePath = "E:\\20150513\\sss.pdf";
		try {
			SolrClient client = new ConcurrentUpdateSolrClient(SrmipConstants.SOLR_URL, 10, 2);
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
			up.addFile(new File(filePath), "application/pdf");
			up.setParam("uprefix", "ignored_");
			up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
			
			client.request(up);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
}
