package org.javaforever.infinity.dao;

import org.javaforever.infinity.domain.Naming;

public interface DomainDao {
public String generateDomainString(Naming naming, String standardName) throws Exception;
public String generateDomainString(long namingid);
}
