package org.javaforever.infinity.verb;

import java.util.ArrayList;
import java.util.List;

import org.javaforever.infinity.core.Verb;
import org.javaforever.infinity.core.Writeable;
import org.javaforever.infinity.domain.Domain;
import org.javaforever.infinity.domain.Method;
import org.javaforever.infinity.domain.Signature;
import org.javaforever.infinity.domain.Statement;
import org.javaforever.infinity.domain.Type;
import org.javaforever.infinity.domain.Var;
import org.javaforever.infinity.generator.NamedStatementGenerator;
import org.javaforever.infinity.generator.NamedStatementListGenerator;
import org.javaforever.infinity.utils.InterVarUtil;
import org.javaforever.infinity.utils.StringUtil;
import org.javaforever.infinity.utils.WriteableUtil;

public class SearchByName extends Verb{

	@Override
	public Method generateDaoImplMethod(){
		try {
			Method method = new Method();
			method.setStandardName("search"+this.domain.getPlural()+"By"+StringUtil.capFirst(this.domain.getDomainName().getFieldName()));	
			method.setReturnType(new Type("List",this.domain, this.domain.getPackageToken()));
			method.addAdditionalImport("java.sql.Connection");
			method.addAdditionalImport("java.util.List");
			method.addAdditionalImport("java.util.ArrayList");
			method.addAdditionalImport("java.sql.PreparedStatement");
			method.addAdditionalImport("java.sql.ResultSet");
			method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
			method.addAdditionalImport(this.domain.getPackageToken()+".dao."+this.domain.getStandardName()+"Dao");
			method.addSignature(new Signature(1,"connection",new Type("Connection","java.sql"),"java.sql"));
			method.addSignature(new Signature(2,this.domain.getDomainName().getFieldName(),new Type("String")));
			method.setThrowException(true);
			
			List<Writeable> list = new ArrayList<Writeable>();
			list.add(NamedStatementGenerator.getTryHead(1000L,2));
			list.add(NamedStatementListGenerator.generateSearchByNameStatementList(2000L,3,this.domain));
			list.add(NamedStatementListGenerator.generateCatchExceptionPrintStackReturnNullFooter(3000L,2));
			method.setMethodStatementList(WriteableUtil.merge(list));
			return method;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String generateDaoImplMethodString(){
		Method m = this.generateDaoImplMethod();
		String s = m.generateMethodString();
		return s;
	}

	@Override
	public Method generateDaoMethodDefinition() {
		Method method = new Method();
		method.setStandardName("search"+this.domain.getPlural()+"By"+StringUtil.capFirst(this.domain.getDomainName().getFieldName()));
		method.setReturnType(new Type("List",this.domain, this.domain.getPackageToken()));
		method.addAdditionalImport("java.sql.Connection");
		method.addAdditionalImport("java.util.List");
		method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
		method.addSignature(new Signature(1,"connection",new Type("Connection","java.sql"),"java.sql"));
		method.addSignature(new Signature(2,this.domain.getDomainName().getFieldName(),new Type("String")));
		method.setThrowException(true);
		
		return method;
	}

	@Override
	public String generateDaoMethodDefinitionString() {
		return generateDaoMethodDefinition().generateMethodDefinition();
	}

	@Override
	public String generateDaoImplMethodStringWithSerial() {
		Method m = this.generateDaoImplMethod();
		m.setContent(m.generateMethodContentStringWithSerial());
		m.setMethodStatementList(null);
		return m.generateMethodString();
	}

	@Override
	public Method generateServiceMethodDefinition() {
		Method method = new Method();
		method.setStandardName("search"+this.domain.getPlural()+"By"+StringUtil.capFirst(this.domain.getDomainName().getFieldName()));
		method.setReturnType(new Type("List",this.domain, this.domain.getPackageToken()));
		method.addAdditionalImport("java.util.List");
		method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
		method.addSignature(new Signature(1,this.domain.getDomainName().getFieldName(),new Type("String")));
		method.setThrowException(true);
		
		return method;
	}

	@Override
	public String generateServiceMethodDefinitionString() {
		return generateServiceMethodDefinition().generateMethodDefinition();
	}

	@Override
	public Method generateControllerMethod() {
		Method method = new Method();
		method.setStandardName("processRequest");
		method.setReturnType(new Type("void"));
		method.setThrowException(true);
		List<String> list = new ArrayList<String>();
		list.add("ServletException");
		list.add("IOException");
		method.setIsprotected(true);
		method.setOtherExceptions(list);
		method.addSignature(new Signature(1,"request",new Type("HttpServletRequest","javax.servlet.http")));
		method.addSignature(new Signature(2,"response",new Type("HttpServletResponse","javax.servlet.http")));
		method.addAdditionalImport("java.io.IOException");
		//method.addAdditionalImport("java.io.PrintWriter");
		method.addAdditionalImport("javax.servlet.ServletException");
		method.addAdditionalImport("javax.servlet.http.HttpServlet");
		method.addAdditionalImport("javax.servlet.http.HttpServletRequest");
		method.addAdditionalImport("javax.servlet.http.HttpServletResponse");
		method.addAdditionalImport("java.util.List");
		method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
		method.addAdditionalImport(this.domain.getPackageToken()+".service."+this.domain.getStandardName()+"Service");
		method.addAdditionalImport(this.domain.getPackageToken()+".serviceimpl."+this.domain.getStandardName()+"ServiceImpl");
		
		List<Writeable> wlist = new ArrayList<Writeable>();
		Var service = new Var("service", new Type(this.domain.getStandardName()+"Service",this.domain.getPackageToken()));
		Var vlist = new Var("list", new Type("List",this.domain,this.domain.getPackageToken()));
		Method serviceMethod = this.generateServiceMethodDefinition();
		wlist.add(NamedStatementGenerator.getControllerSetContentType(1000L, 2, InterVarUtil.Servlet.response, InterVarUtil.SimpleJEE.UTF8.getVarName()));
		wlist.add(NamedStatementGenerator.getTryHead(2000L,2));
		wlist.add(NamedStatementGenerator.getSetDomainNameFromRequest(3000L, 3, this.domain, InterVarUtil.Servlet.request));
		wlist.add(new Statement(4000,3,service.generateTypeVarString() + " = new " + service.getVarType().getTypeName()+"Impl();"));
		wlist.add(new Statement(5000,3, vlist.generateTypeVarString() + " = " + service.getVarName()+"."+serviceMethod.generateStandardCallString()+";"));
		wlist.add(new Statement(6000,3,InterVarUtil.Servlet.request.getVarName()+".setAttribute(\""+StringUtil.lowerFirst(this.domain.getStandardName())+"List\","+vlist.getVarName()+");"));
		wlist.add(new Statement(6500,3,InterVarUtil.Servlet.request.getVarName()+".setAttribute(\""+StringUtil.lowerFirst(this.domain.getDomainName().getFieldName())+"\","+StringUtil.lowerFirst(this.domain.getDomainName().getFieldName())+");"));
		wlist.add(NamedStatementGenerator.getControllerForward(7000L,3, InterVarUtil.Servlet.response, InterVarUtil.Servlet.request, "../jsp/"+this.domain.getPlural().toLowerCase()+".jsp"));
		wlist.add(NamedStatementListGenerator.generateCatchExceptionPrintStackFooter(8000L, 2));

		method.setMethodStatementList(WriteableUtil.merge(wlist));
		
		return method;
	}

	@Override
	public String generateControllerMethodString() {
		return generateControllerMethod().generateMethodString();
	}

	@Override
	public Method generateServiceImplMethod() {
		Method method = new Method();
		method.setStandardName("search"+this.domain.getPlural()+"By"+StringUtil.capFirst(this.domain.getDomainName().getFieldName()));
		method.setReturnType(new Type("List",this.domain, this.domain.getPackageToken()));
		method.addAdditionalImport("java.sql.Connection");
		method.addAdditionalImport("java.util.List");
		method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
		method.addAdditionalImport(this.domain.getPackageToken()+".database.DBConf");
		method.addAdditionalImport(this.domain.getPackageToken()+".dao."+this.domain.getStandardName()+"Dao");
		method.addAdditionalImport(this.domain.getPackageToken()+".daoimpl."+this.domain.getStandardName()+"DaoImpl");
		method.addAdditionalImport(this.domain.getPackageToken()+".service."+this.domain.getStandardName()+"Service");
		method.addSignature(new Signature(1,this.domain.getDomainName().getFieldName(),new Type("String")));
		method.setThrowException(true);
		
		Method daomethod = this.generateDaoMethodDefinition();
		
		List<Writeable> list = new ArrayList<Writeable>();
		list.add(NamedStatementListGenerator.generateServiceImplReturnList(1000L, 2, InterVarUtil.DB.connection, InterVarUtil.DB.dbconf, this.domain, InterVarUtil.DB.dao, daomethod));
		method.setMethodStatementList(WriteableUtil.merge(list));

		return method;
	}

	@Override
	public String generateServiceImplMethodString() {
		return generateServiceImplMethod().generateMethodString();
	}

	@Override
	public String generateServiceImplMethodStringWithSerial() {
		Method m = this.generateServiceImplMethod();
		m.setContent(m.generateMethodContentStringWithSerial());
		m.setMethodStatementList(null);
		return m.generateMethodString();
	}
	
	public SearchByName(){
		super();
	}
	
	public SearchByName(Domain domain){
		super();
		this.domain = domain;
		this.setVerbName("Search"+this.domain.getPlural()+"By"+StringUtil.capFirst(this.domain.getDomainName().getFieldName()));
	}

	@Override
	public String generateControllerMethodStringWithSerial() {
		Method m = this.generateControllerMethod();
		m.setContent(m.generateMethodContentStringWithSerial());
		m.setMethodStatementList(null);
		return m.generateMethodString();
	}

	@Override
	public Method generateFacadeMethod() {
		Method method = new Method();
		method.setStandardName("processRequest");
		method.setReturnType(new Type("void"));
		method.setThrowException(true);
		List<String> list = new ArrayList<String>();
		list.add("ServletException");
		list.add("IOException");
		method.setIsprotected(true);
		method.setOtherExceptions(list);
		method.addSignature(new Signature(1,"request",new Type("HttpServletRequest","javax.servlet.http")));
		method.addSignature(new Signature(2,"response",new Type("HttpServletResponse","javax.servlet.http")));
		method.addAdditionalImport("java.io.IOException");
		method.addAdditionalImport("java.io.PrintWriter");
		method.addAdditionalImport("javax.servlet.ServletException");
		method.addAdditionalImport("javax.servlet.http.HttpServlet");
		method.addAdditionalImport("javax.servlet.http.HttpServletRequest");
		method.addAdditionalImport("javax.servlet.http.HttpServletResponse");
		method.addAdditionalImport("java.util.List");
		method.addAdditionalImport("java.util.Map");
		method.addAdditionalImport("java.util.TreeMap");
		method.addAdditionalImport("net.sf.json.JSONObject");
		method.addAdditionalImport(this.domain.getPackageToken()+".domain."+this.domain.getStandardName());
		method.addAdditionalImport(this.domain.getPackageToken()+".service."+this.domain.getStandardName()+"Service");
		method.addAdditionalImport(this.domain.getPackageToken()+".serviceimpl."+this.domain.getStandardName()+"ServiceImpl");
		
		List<Writeable> wlist = new ArrayList<Writeable>();
		Var service = new Var("service", new Type(this.domain.getStandardName()+"Service",this.domain.getPackageToken()));
		Var resultMap = new Var("result", new Type("TreeMap<String,Object>","java.util"));
		Var vlist = new Var("list", new Type("List",this.domain,this.domain.getPackageToken()));
		Method serviceMethod = this.generateServiceMethodDefinition();
		wlist.add(NamedStatementGenerator.getFacadeSetContentType(1000L, 2, InterVarUtil.Servlet.response, InterVarUtil.SimpleJEE.UTF8.getVarName()));
		wlist.add(NamedStatementGenerator.getJsonResultMap(2000L, 2, resultMap));
		wlist.add(NamedStatementGenerator.getControllerPrintWriterOut(3000L, 2, InterVarUtil.Servlet.response, InterVarUtil.Servlet.out));
		wlist.add(NamedStatementGenerator.getTryHead(4000L,2));
		wlist.add(NamedStatementGenerator.getSetDomainNameFromRequest(5000L, 3, this.domain, InterVarUtil.Servlet.request));
		wlist.add(new Statement(6000,3,service.generateTypeVarString() + " = new " + service.getVarType().getTypeName()+"Impl();"));
		wlist.add(new Statement(7000,3, vlist.generateTypeVarString() + " = " + service.getVarName()+"."+serviceMethod.generateStandardCallString()+";"));
		wlist.add(NamedStatementListGenerator.getPutJsonResultMapWithSuccessAndDomainList(8000L, 3, resultMap,vlist));
		wlist.add(NamedStatementGenerator.getEncodeMapToJsonResultMap(9000L, 3, resultMap,InterVarUtil.Servlet.out));
		wlist.add(NamedStatementListGenerator.generateCatchExceptionPrintStackPrintJsonMapFinallyCloseOutFooter(10000L, 2, InterVarUtil.Servlet.response,resultMap, InterVarUtil.Servlet.out));
		
		method.setMethodStatementList(WriteableUtil.merge(wlist));		
		return method;
	}

	@Override
	public String generateFacadeMethodString() {
		Method m = this.generateFacadeMethod();
		return m.generateMethodString();
	}

	@Override
	public String generateFacadeMethodStringWithSerial() {
		Method m = this.generateFacadeMethod();
		m.setContent(m.generateMethodContentStringWithSerial());
		m.setMethodStatementList(null);
		return m.generateMethodString();
	}
}
