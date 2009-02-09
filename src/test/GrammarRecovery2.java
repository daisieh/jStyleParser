package test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.tidy.Tidy;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import cz.vutbr.web.css.NodeData;
import cz.vutbr.web.css.RuleMedia;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;
import cz.vutbr.web.css.TermFactory;
import cz.vutbr.web.css.TermList;
import cz.vutbr.web.css.CSSProperty.FontFamily;
import cz.vutbr.web.domassign.Analyzer;

public class GrammarRecovery2 {
	private static Logger log = LoggerFactory.getLogger(GrammarRecovery1.class);

	public static final TermFactory tf = CSSFactory.getTermFactory();

	public static final String TEST_DECL1A = "p { color: red; _width: 10em; }"; /* vendor-specific extension */
    public static final String TEST_DECL1B = "p { _width: 10em; color: red; }";

    public static final String TEST_DECL2A = "p { color: red; -width: 10em; }"; /* vendor-specific extension */
    public static final String TEST_DECL2B = "p { -width: 10em; color: red; }";
    
    public static final String TEST_DECL3A = "p { color: red; :width: 10em; }"; /* invalid character */
    public static final String TEST_DECL3B = "p { :width: 10em; color: red; }";
    
    public static final String TEST_DECL4A = "p { color: red; *width: 10em; }"; /* invalid character */
    public static final String TEST_DECL4B = "p { *width: 10em; color: red; }";
    
	@BeforeClass
	public static void init() 
	{
		log.info("\n\n\n == GrammarRecovery2 test at {} == \n\n\n", new Date());
	}

	@Test
	public void vendorSpecificUnderscore() throws IOException, CSSException 
	{
		StyleSheet ss = CSSFactory.parse(TEST_DECL1A);
		assertEquals("Both properties are accepted (second one is extension)", 2, ss.size());
        ss = CSSFactory.parse(TEST_DECL1B);
        assertEquals("Both properties are accepted (first one is extension)", 2, ss.size());
	}

    @Test
    public void vendorSpecificDash() throws IOException, CSSException 
    {
        StyleSheet ss = CSSFactory.parse(TEST_DECL2A);
        assertEquals("Both properties are accepted (second one is extension)", 2, ss.size());
        ss = CSSFactory.parse(TEST_DECL2B);
        assertEquals("Both properties are accepted (first one is extension)", 2, ss.size());
    }
    
    @Test
    public void invalidCharColon() throws IOException, CSSException 
    {
        StyleSheet ss = CSSFactory.parse(TEST_DECL3A);
        assertEquals("One property is accepted (second one is invalid)", 1, ss.size());
        ss = CSSFactory.parse(TEST_DECL3B);
        assertEquals("One property is accepted (first one is invalid)", 1, ss.size());
    }
    
    @Test
    public void invalidCharAsterisk() throws IOException, CSSException 
    {
        StyleSheet ss = CSSFactory.parse(TEST_DECL4A);
        assertEquals("One property is accepted (second one is invalid)", 1, ss.size());
        ss = CSSFactory.parse(TEST_DECL4B);
        assertEquals("One property is accepted (first one is invalid)", 1, ss.size());
    }

}
