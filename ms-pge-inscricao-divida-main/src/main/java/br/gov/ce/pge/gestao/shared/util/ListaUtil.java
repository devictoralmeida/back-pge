package br.gov.ce.pge.gestao.shared.util;

import java.util.HashSet;
import java.util.List;

public class ListaUtil {
	
	private ListaUtil() {}
	
	public static boolean isEquals(List<?> list1, List<?> list2) {
	    return new HashSet<>(list1).equals(new HashSet<>(list2));
	}

}
