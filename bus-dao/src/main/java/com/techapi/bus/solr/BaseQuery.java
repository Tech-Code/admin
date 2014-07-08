package com.techapi.bus.solr;

import com.techapi.bus.entity.CityStation;
import com.techapi.bus.util.ConfigUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author hongjia.hu
 * @Date 2014-5-26
 */
public class BaseQuery {

	protected HttpSolrServer server;

	private long numFound = 0;

	public BaseQuery() {
		try {
			server = new HttpSolrServer(ConfigUtils.SOLR_URL);
			server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
			server.setConnectionTimeout(50000);
			server.setSoTimeout(10000); // socket read timeout
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
			server.setFollowRedirects(false); // defaults to false
			server.setAllowCompression(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param list
	 */
	protected void updateBeans(List<?> list) {
		// TODO Auto-generated method stub
		try {
            List<String> ids = new ArrayList<>();
            for(Object cs : list) {
                ids.add(((CityStation) cs).getId());
            }
            server.deleteById(ids);
			server.addBeans(list);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void updateBean(Object obj) {
		// TODO Auto-generated method stub
		try {
            String id = ((CityStation)obj).getId();
            server.deleteById(id);
			server.addBean(obj);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Object> queryBeans(String q, int start, int rows, Class<?> cls) {
		SolrQuery query = new SolrQuery(q);
		query.setStart(start);
		query.setRows(rows);
		try {
            QueryResponse response = server.query(query);
			List<Object> result = new ArrayList<Object>();
			SolrDocumentList list = response.getResults();
			numFound = list.getNumFound();
			for (int i = 0; i < list.size(); i++) {
				Object obj = cls.newInstance();
				SolrDocument doucument = list.get(i);
                String cityStationName = doucument.getFieldValue("cityStationName").toString();
                String id = doucument.getFieldValue("id").toString();
                CityStation cityStation = new CityStation();
                cityStation.setStationName(cityStationName);
                cityStation.setId(id);
				result.add(cityStation);
			}

			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

    public void deleteBeanById(String id) {
        try {
            server.deleteById(id);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBeanByIds(List<String> ids) {
        try {
            server.deleteById(ids);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBean(String query) {
        try {
            server.deleteByQuery(query);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}
}
