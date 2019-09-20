package mühendislikprojesi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.lang.Math.pow;
import static java.sql.Types.NULL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultEditorKit;
public class MühendislikProjesi {
    public static void main(String[] args) {
        int toplam=0,k=0,m=0;
        int krktr = 0;
        String dizi[]=new String [100];
        int array[]=new int [100];
        int hash = 0;
        String aranilan =null;
        File f=new File("kelime.txt ");             //dosya olusturuluyor
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            System.out.println("INDIS\t\tKELIME\t\t%211\t\tTABLE[HASH]");
            System.out.println("**********************************************************");
            for(int i=0;i<100;i++){                              
                String s=reader.readLine();             //dosyadaki kelimeler satır satır okunuyor
                s=s.toLowerCase();                      //dosyadaki kelimenin büyük harlerini küçük harfe çeviriyor
                dizi[i]=s;                              //kelimeler bir diziye atanıyor
                for(int j=0;j!=s.length();j++){                    //ascii değerleri buluyor
                    krktr=dizi[i].charAt(j);
                    toplam=toplam+krktr*(k+1);                  //ascii değerlerinin toplamını alıyor                                       
                    k++;
                    
                }
                hash=toplam%211;                                 //hash degeri hesaplandı
                array[m]=toplam;                                //ascii degerleri array dizisine atandı
                System.out.print((i+1)+"   "+"\t\t"+dizi[i]);      //kelime
                System.out.print("\t\t "+hash);                      //key
                HASH(hash,toplam);                          //HASH fonksiyonuna gonderiliyor
                toplam=0;
                k=0;              
                m++;
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        System.out.println("**********************************************************");
        System.out.println("aramak istediginiz kelimeyi giriniz: ");
        Scanner gir=new Scanner(System.in);                 //aranılacak kelime giriliyor
        aranilan=gir.nextLine();
        arama(aranilan,array);                          //arama fonksiyonuna gonderiliyor
    } 
    public static void HASH(int hash,int toplam){
        int Hash = 0;
        int t=0;
        int table[]=new int [211];
        Hash=hash;                         //bulunan hash degeri baska bir degişkene atıldı
        table[Hash]=toplam;                 //ascii degeri tablonun hash yerine yerlestirildi
        if(table[hash]== 0){                       // tabloya hashler yerlestirildi                                
            toplam=(int)((hash+pow(t,4))%211);
            t++;
            if(hash >= 211) 
                hash = 0;
        }
        table[Hash]= toplam;                                             
        System.out.println("\t\t   "+table[Hash]);         
    }   
    public static void arama(String aranilan, int[] array) {
        int array1[]=new int [100];
        char array2[]=new char [100];
        int k=0,toplam=0,j,ascii = 0,sayac=0,i=0;
        char krktr;
        int size=aranilan.length();
        for(j=0;j!=size;j++){                           //ascii kodlarını buluyor
            krktr = aranilan.charAt(j);
            toplam=toplam+krktr*(k+1);                  //ascii kodlarının toplamını alıyor                                       
            k++;  
        }
        for(i=0;i<100;i++){
            ascii = array[i]; 
            if(toplam==ascii) {                                //aranılanla dizideki kelimelerin asciileri karsılastırılıyor
                System.out.println("aranılan kelime dosyanızda mevcuttur.");
                sayac++;
            } 
        }
        
        if(i>=100 && sayac==0){ //i kelime sayısından buyuk ve sayac 0 a esitse dosyada yoktur
                    System.out.println("aranılan kelime dosyanızda yoktur.");                                          
                }
        if(sayac==0){                      
            //  EKSİLTMELİ ARAMA
            String eksik="";
            for(i=0;i!=size;i++){
                int sayac1 = 0;
                int l=0;j=0;
                if(j!=size-1){
                    for( sayac1=0;sayac1!=size-1;sayac1++){
                        if(j==i){                   //j eksiltecek karakterle aynıysa l 1 arttırılır
                            l++;
                        }
                        krktr = aranilan.charAt(sayac1+l);          //kelimenin sayac+l karakteri buluyor
                        array2[sayac1]=krktr;                       
                        eksik=new String(array2);               //kelimenin eksik hali eksik'e atanıyor
                        j++;                    
                    }
                } 
                int boyut=eksik.length();           //eksiltilmis kelimenin boyutu hesaplanıyor
                k=0;
                int plus=0;l=0;
                for(int m=0;m<boyut;m++){
                    krktr=eksik.charAt(m);          //karakterler bulunuyor 
                    plus = plus+krktr*(k+1);        //ascii kodlarının toplamını buluyor                                       
                    k++;  
                }
                array1[l]=plus;
                for(j=0;j<100;j++){
                    for(l=0;l!=boyut;l++){
                       if(array1[l]==array[j]){     //eksiltilmis kelimenin asciisiyle dizideki keimelerin asciileri karsilastırılıyor
                           System.out.println("Girdiginiz "+aranilan+" kelimesi yerine eksiltilmiş olan kelime: "+eksik);
                       }                           
                    }
                }
            }
            //YER DEGİŞTİRMELİ ARAMA
            String yerDgstrme="";
            char gecici=0;  
            for(i=0;i<size-1;i++){    
                for(j=0;j<size;j++){                    //aranilan kelimenin karakterleri diziye atanıyor
                    array2[j]=aranilan.charAt(j);
                }
                gecici = array2[i];                // kelimenin yerleri degistiriliyor
                array2[i]=array2[i+1];
                array2[i+1]=gecici;
                yerDgstrme=new String(array2);          //kelimenin yer degistirilmis degeri yerDgstrme'ye atanıyor
                int boyut = yerDgstrme.length();
                k=0;
                int plus=0, l = 0;
                for(int m=0;m<boyut;m++){
                    krktr=yerDgstrme.charAt(m);         //karakterler bulunuyor
                    plus = plus+krktr*(k+1);            //ascii kodlarının toplamını alıyor                                       
                    k++;  
                }
                array1[l]=plus;
                for(j=0;j<100;j++){
                    for(l=0;l!=boyut;l++){
                       if(array1[l]==array[j]){    //yer degistirilmis kelimenin asciisiyle dizideki keimelerin asciileri karsilastırılıyor
                           System.out.println("Girdiginiz "+aranilan+" kelimesi yerine yer değiştirilmiş olan kelime: "+yerDgstrme);
                       }                           
                    }
                }
            }
        }
    }           
}