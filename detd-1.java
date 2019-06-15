

package domain;


import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebClass{
    private static Object st1;

	public static void main(String[] args) throws SocketTimeoutException, UnknownHostException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, IOException 
	{
		PrintWriter writer = new PrintWriter("links.txt", "UTF-8");
		
		String dnm,ch;
		String title,dlnk;
		int lnkcnt=0,dmncnt=0;
		int flag=0;
		int Domain_Visit_Count=0;
		Connection con=null;
		InputStreamReader reader=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(reader);
		
		LinkedList<String> domain_list=new LinkedList<String>(); 
		LinkedList<String> Link_Queue=new LinkedList<String>(); 
		
		LinkedList<String> DOMAIN_VISITED=new LinkedList<String>(); 
		LinkedList<String> LINK_VISITED=new LinkedList<String>(); 
        
       
			
		System.out.println("\n\n\t\tVendos adresen : ");
		
		try {
		do
		{
			System.out.print("\t\tShkruaje ketu : ");
			dnm=br.readLine();
			domain_list.add(new String("http://"+dnm));
				
                        System.out.print("\t\tDeshironi te shtoni nje tjeter?(Y/N) : ");
			ch=br.readLine();
					
		}while(!((ch.equals("N")) || (ch.equals("n"))));
				
                    Console console;
                    console = System.console();
		while(!domain_list.isEmpty())
		{
                    String domain_element=new URL(domain_list.poll()).getHost();
                    Link_Queue.add(new String("http://"+domain_element));
					

                    Document docd=Jsoup.connect("http://"+dnm).userAgent("Mozilla Firefox").get();
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.println("\t \tDomain i vizituar :  "+domain_element);
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    writer.println("Domain u kontrrollua"+domain_element);
                    
                    dmncnt++;
                    
                    Domain_Visit_Count++;
                    flag=0;
					
					
                    Elements metas=null;
                    metas=docd.select("meta");
                    
                    for(Element meta : metas )
                    {
                        
			if(meta.attr("name").equals("keywords")||meta.attr("name").equals("description"))
			{
				flag=1;
			}
                    }
				
                    
                    if(flag==0){	
                        
                    }
					
                    DOMAIN_VISITED.add(domain_element);
					
                    try
                    {
                        while(!(Link_Queue.isEmpty()))		
			{
						
                            String url=Link_Queue.poll();	 
                            con=Jsoup.connect(url);
					
                            if(con!=null)
                            {
                        
									
                                Document doc=Jsoup.connect(url).timeout(5000).userAgent("Mozilla Firefox").get();
				
                                LINK_VISITED.add(url);
				Link_Visit_Count++;
				lnkcnt++;
							 			
									
				int lflag=0;
				Elements lmetas=null;
				lmetas=doc.select("meta");
									
				for(Element lmeta : lmetas )
				{
									
                                    if(lmeta.attr("name").equals("keywords")||lmeta.attr("name").equals("description"))
                                    {
											
					lflag=1;
                                    }
				}
                                
										
				if(lflag==0){
            
										
				Elements elmnts=doc.select("a");
										
				for(Element elmnt:elmnts)
				{
								
					String lnk=elmnt.attr("abs:href");
											
                                        if(!lnk.equals(""))
					{
												
                                            if(!Link_Queue.contains(lnk))			
                                            {
						lnkcnt++;
										 
                                            }	
									
									
                                            if(!domain_element.equals(new URL(lnk).getHost()))
                                            {
										
						if(!DOMAIN_VISITED.contains(new URL(lnk).getHost()))
						{
                                                	dmncnt++;
							domain_list.add(new String("http://"+new URL(lnk).getHost()));
							DOMAIN_VISITED.add(new URL(lnk).getHost());
                                                        System.out.println("\tUgjet domain i ri : ["+dmncnt+"] : "+new URL(lnk).getHost()+"\n");
                  
						writer.println("\nUgjet domain i ri : ["+dmncnt+"] : "+new URL(lnk).getHost()+"\n");
                                                }								
										
                                            }
                                            
									
					}
										
				} 
									
			}	
						
						
                    }
					
		writer.close();

					}
				}
				
			
		catch(IOException e)
		{
                    System.out.println("\n\t\t"+e);
		}
				
				
		}	
				
				
		}	
		
		catch(IOException e)
		{
			System.out.println("\n\t\t"+e);
			
		}
		
		
		finally
		{
			System.out.println("\n\t\tSasia e domain te vizituar : "+Domain_Visit_Count+"\n\t\tSasia e link te vizituar   : "+Link_Visit_Count);
		}

	}	

}
