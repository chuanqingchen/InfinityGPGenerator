package test.javaforever.infinity.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.javaforever.infinity.domain.Domain;
import org.javaforever.infinity.domain.Method;
import org.javaforever.infinity.domain.Signature;
import org.javaforever.infinity.domain.Type;
import org.junit.Test;

public class DomainTest extends TestCase {
	@Test
	public void testGenerateClassString(){
		Domain domain = new Domain();
		domain.setPackageToken("com.javaforever.infinity");
		domain.setStandardName("Employee");
		domain.addField("EmployeeId","long");
		domain.addField("Name", "String");
		domain.addField("Gender", "String");
		domain.addField("Age", "int");
		domain.addField("EmployeeDescription", "String");
		domain.addField("EmployeeComment","String");
		domain.addField("updateTime","Timestamp", "java.sql");
		Method promotion = new Method();
		promotion.setStandardName("Promotion");
		List<Signature> signatures = new ArrayList<Signature>();
		signatures.add(new Signature(1,"employeeId",new Type("long")));
		signatures.add(new Signature(1,"positionId",new Type("long")));
		promotion.setSignatures(signatures);
		//promotion.setReturnType("void");
		domain.addMethod(promotion);
		
		System.out.println(domain.generateClassString());
	}
}
