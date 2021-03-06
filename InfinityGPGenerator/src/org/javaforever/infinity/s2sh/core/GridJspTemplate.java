package org.javaforever.infinity.s2sh.core;

import java.util.ArrayList;
import java.util.List;

import org.javaforever.infinity.core.Verb;
import org.javaforever.infinity.core.Writeable;
import org.javaforever.infinity.domain.Domain;
import org.javaforever.infinity.domain.Field;
import org.javaforever.infinity.domain.Method;
import org.javaforever.infinity.domain.Statement;
import org.javaforever.infinity.domain.StatementList;
import org.javaforever.infinity.domain.Type;
import org.javaforever.infinity.domain.Var;
import org.javaforever.infinity.exception.ValidateException;
import org.javaforever.infinity.generator.JspTemplate;
import org.javaforever.infinity.s2sh.verb.S2SHVerbFactory;
import org.javaforever.infinity.utils.InterVarUtil;
import org.javaforever.infinity.utils.StringUtil;
import org.javaforever.infinity.utils.WriteableUtil;
import org.javaforever.infinity.verb.Add;
import org.javaforever.infinity.verb.Delete;
import org.javaforever.infinity.verb.FindById;
import org.javaforever.infinity.verb.FindByName;
import org.javaforever.infinity.verb.ListActive;
import org.javaforever.infinity.verb.ListAll;
import org.javaforever.infinity.verb.SearchByName;
import org.javaforever.infinity.verb.SoftDelete;
import org.javaforever.infinity.verb.Update;

public class GridJspTemplate extends JspTemplate {
	protected Action action;
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public GridJspTemplate(Domain domain) throws Exception{
		super();
		this.domain = domain;
		this.standardName = StringUtil.lowerFirst(domain.getPlural());
		this.verbs = new ArrayList<Verb>();
		this.verbs.add(new ListAll(domain));
		this.verbs.add(new ListActive(domain));
		this.verbs.add(new Delete(domain));
		this.verbs.add(new FindById(domain));
		this.verbs.add(new FindByName(domain));
		this.verbs.add(new SearchByName(domain));
		this.verbs.add(new SoftDelete(domain));
		this.verbs.add(new Update(domain));
		this.verbs.add(new Add(domain));
		this.action = new Action(this.verbs,this.domain);
	}
	
	public String generateJspString(){
		return generateStatementList().getContent();
	}
	
	@Override
	public StatementList generateStatementList() {
		try {
			List<Writeable> sList =  new ArrayList<Writeable>();
			sList.add(new Statement(1000,0,"<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>"));
			sList.add(new Statement(2000,0,"<%@page import=\"java.util.List\" %>"));
			sList.add(new Statement(3000,0,"<%@page import=\""+this.getDomain().getPackageToken()+".domain."+this.getDomain().getStandardName()+"\"%>"));
			sList.add(new Statement(6000,0,"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"));
			sList.add(new Statement(7000,0,"<html xmlns=\"http://www.w3.org/1999/xhtml\">"));
			sList.add(new Statement(8000,0,"<head>"));
			sList.add(new Statement(9000,0,"<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"));
			sList.add(new Statement(10000,0,"<title>"+StringUtil.capFirst(this.getStandardName())+" Info.</title>"));
			sList.add(new Statement(11000,0,"<link href=\"../css/default.css\" rel=\"stylesheet\" type=\"text/css\" />"));
			sList.add(new Statement(12000,0,"</head>"));  
			sList.add(new Statement(13000,0,"<body>"));
			sList.add(new Statement(14000,1,"<div id=\"wrapper\">"));
			sList.add(new Statement(15000,1,"<jsp:include page=\"../include/header.jsp\" />"));
			sList.add(new Statement(16000,1,"<!-- end div#header -->"));
			sList.add(new Statement(17000,1,"<div id=\"page_wide\">"));
			sList.add(new Statement(18000,1,"<div id=\"mycontent\">"));
			sList.add(new Statement(19000,1,"<div id=\"welcome\">"));
			sList.add(new Statement(20000,1,"<!-- Fetch Rows -->"));
			Method searchMethod = action.findVerbMethodByName(S2SHVerbFactory.getInstance("searchByName",this.domain).getMethod().getLowerFirstMethodName());
			Field searchByNameField = this.domain.getDomainName();
			sList.add(S2SHJSPNamedStatementListGenerator.generateSearchByNameTableFormWithRequestVarStatementList(20500, 1, this.domain, searchByNameField, searchMethod));
		
			sList.add(new Statement(21000,1,"<table class=\"aatable\">"));
			sList.add(S2SHJSPNamedStatementGenerator.getTrStartStatement(22000, 1));
			sList.add(S2SHJSPNamedStatementListGenerator.generateTableColumnHeadsUsingIdRenameFieldListWithOperationColumnStatementList(23000, 1, this.domain));
			sList.add(S2SHJSPNamedStatementGenerator.getTrEndStatement(28000, 1));
			
			// loop through data list
			sList.add(S2SHJSPNamedStatementGenerator.getJavaNotionStartStatement(29000,1));

			Var list = new Var("list", new Type("List<"+this.domain.getStandardName()+">","java.util"));
			Var request = InterVarUtil.Servlet.request;
			sList.add(NamedS2SHStatementGenerator.getDomainListFromRequestAttribute(30000, 1, this.domain, list, request));
			Var index = new Var("index", new Type("int"));
			sList.add(NamedS2SHStatementGenerator.getListForHead(31000, 1, this.domain,index, list));		
			sList.add(NamedS2SHStatementGenerator.getDomainFromListi(31500, 2, domain, list, index));
			sList.add(S2SHJSPNamedStatementGenerator.getJavaNotionEndStatement(32000,1));
			sList.add(S2SHJSPNamedStatementGenerator.getTrStartStatement(33000,1));
			Method updateMethod = action.findVerbMethodByName(S2SHVerbFactory.getInstance("update",this.domain).getMethod().getLowerFirstMethodName());
			sList.add(new Statement(34000,1,"<form action='../action/"+StringUtil.lowerFirst(updateMethod.getStandardName())+"' method='post'>"));
			sList.add(S2SHJSPNamedStatementListGenerator.generateTableColumnsUsingIdRenameFieldListStatementList(35000,1,this.domain));
			Var mydomain = new Var(StringUtil.lowerFirst(domain.getStandardName()),new Type(StringUtil.lowerFirst(domain.getStandardName()),domain.getPackageToken()));         
			sList.add(new Statement(35000,1,"<td>"));
			sList.add(S2SHJSPNamedStatementGenerator.getHideInputDomainIdStatement(36000, 1, this.domain,mydomain));
			sList.add(new Statement(37000,1,"<input type='submit' value='Edit' /></form>"));
			Method deleteMethod = action.findVerbMethodByName(S2SHVerbFactory.getInstance("delete",this.domain).getMethod().getLowerFirstMethodName());
			Method softDeleteMethod = action.findVerbMethodByName(S2SHVerbFactory.getInstance("softDelete",this.domain).getMethod().getLowerFirstMethodName());
			sList.add(S2SHJSPNamedStatementListGenerator.generateDeleteUsingHiddenIdStatementList(38000, 1, this.domain, deleteMethod));
			sList.add(S2SHJSPNamedStatementListGenerator.generateSoftDeleteUsingHiddenIdStatementList(38500, 1, this.domain, softDeleteMethod));
			sList.add(S2SHJSPNamedStatementGenerator.getTrEndStatement(39000, 1));
			sList.add(S2SHJSPNamedStatementGenerator.getLoopEndWithJavaNotionStatement(39500, 1));
			//add functional
			sList.add(S2SHJSPNamedStatementGenerator.getTrStartStatement(39700, 1));
			Method addMethod = action.findVerbMethodByName(S2SHVerbFactory.getInstance("add",this.domain).getMethod().getLowerFirstMethodName());
			sList.add(S2SHJSPNamedStatementGenerator.getSetAddFormStatement(40000,1, this.domain, addMethod));
			sList.add(S2SHJSPNamedStatementListGenerator.generateTableRowsUsingIdRenameFieldListWithoutSettingValueStatementList(41000,1,this.domain));
			sList.add(S2SHJSPNamedStatementGenerator.getTdStartStatement(42000, 2));
            sList.add(S2SHJSPNamedStatementGenerator.getSubmitButtonStatement(43000, 2,"Add")); 
            sList.add(S2SHJSPNamedStatementGenerator.getTdEndStatement(44000, 2));
            sList.add(S2SHJSPNamedStatementGenerator.getFormEndStatement(45000, 1));
            sList.add(S2SHJSPNamedStatementGenerator.getTrEndStatement(46000, 1));
            sList.add(S2SHJSPNamedStatementGenerator.getTableEndStatement(47000, 1));
            sList.add(S2SHJSPNamedStatementGenerator.getDivEndStatement(48000, 1));
			sList.add(new Statement(54000,1,"<!-- end div#welcome -->"));
			sList.add(new Statement(55000,1,"</div>"));
			sList.add(new Statement(56000,1,"<!-- end div#content -->"));
			sList.add(new Statement(57000,1,"<div id=\"sidebar\">"));   
			sList.add(new Statement(58000,1,"<ul>"));
			sList.add(new Statement(59000,1,"<%if (request.getSession().getAttribute(\"isadmin\")!=null && (Boolean)request.getSession().getAttribute(\"isadmin\")){%>"));
			sList.add(new Statement(60000,1,"<jsp:include page=\"../include/adminnav.jsp\"/>"));
			sList.add(new Statement(61000,1,"<%} else { %>"));
			sList.add(new Statement(62000,1,"<jsp:include page=\"../include/usernav.jsp\"/>"));
			sList.add(new Statement(63000,1,"<%} %>"));
			sList.add(new Statement(64000,1,"<!-- end navigation -->"));
			sList.add(new Statement(65000,1,"<jsp:include page=\"../include/updates.jsp\"/>"));
			sList.add(new Statement(66000,1,"<!-- end updates -->"));
			sList.add(new Statement(67000,1,"</ul>"));                   
			sList.add(new Statement(68000,1,"</div>"));
			sList.add(new Statement(69000,1,"<!-- end div#sidebar -->"));
			sList.add(new Statement(70000,1,"<div style=\"clear: both; height: 1px\"></div>"));
			sList.add(new Statement(71000,1,"</div>"));
			sList.add(new Statement(72000,1,"<jsp:include page=\"../include/footer.jsp\"/>"));
			sList.add(new Statement(73000,1,"</div>"));        
			sList.add(new Statement(74000,1,"<!-- end div#wrapper -->"));
			sList.add(S2SHJSPNamedStatementGenerator.getBodyEndStatement(75000, 0));
			sList.add(S2SHJSPNamedStatementGenerator.getHtmlEndStatement(76000, 0));

			StatementList mylist = WriteableUtil.merge(sList);
			return mylist;
		} catch (Exception e){
			return null;
		}
	}
}
