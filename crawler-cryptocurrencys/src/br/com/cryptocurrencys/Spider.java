package br.com.cryptocurrencys;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {
	// Limitite do n�mero de p�ginas para executar a pesquisa
	private static final int MAX_PAGES_TO_SEARCH = 10;
	//Set define cada p�gina (url) visitada como �nica
	private Set<String> pagesVisited = new HashSet<String>();
	//List faz o armazenamento das p�ginas (urls) que ser�o visitadas a seguir
	private List<String> pagesToVisit = new LinkedList<String>();

	private String nextUrl() { //M�todo para definir a pr�xima p�gina a ser visitada
		String nextUrl;   
		

		/* remove a p�gina (url) da lista de pagesToVisit se pagesVisited cont�m a nextUrl
		Se  a p�gina for visitada, ela ser� removida de pagesToVisit, e entrar para
		a lista pagesVisited */
		
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
	
	public void search(String url, String searchWord)
    {
        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            boolean success = false;
			try {
				success = leg.searchForWord(searchWord);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }
}