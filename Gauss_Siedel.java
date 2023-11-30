import java.lang.Math;
import java.util;
import java.util.Scanner;
import java.lang.String;

public class Gauss_Siedel
{
    public static void matris_tanimla(int boyut, double[][] matris)
    {
        //double[][] matris = new double[boyut][boyut + 1];
        Scanner girdi1 = new Scanner(System.in);
        for(int i = 0 ; i < boyut ; i++)
        {
            for(int j = 0 ; j < (boyut + 1) ; j++)
            {
                System.out.println("a(" + i + j + ") değerini giriniz (double olarak): ");
                matris[i][j] = girdi1.nextDouble();
            }
        }
    }

    public static double[][] matrisi_duzenle(double[][] matris, int boyut)
    {
        double xi;  //matrisin o elemanını tutacak
        int satir;  //o an bulunulan satırı tutacak
        int j=0;
        double[][] satirtemp = new double[1][boyut+1];  //satırın yerini değiştirebilmek için onu temp bir yerde tutmalıyım
        while(j <= boyut-1) //her satırın sonunda denklem sonucunu tutan yeri işlem dışı bırakıyorum
        {
            xi = matris[j][j];  //satır sütun için o an denk gelen sayıyı tutuyorum
            int satirsay=j;     //en başta karşılaşmanın yapıldığı satırın sayısını tutuyor
            for(int i=j;i < boyut;i++)  //her defasında düzenlenen ve olmsı gereken yere yerleşen sırayı artık ekarte etmek için
            {
                if(xi <= matris[i][j])  //tutulan sayı gelinen satırdaki değerden küçükse gir
                {
                    xi=matris[i][j];    //yeni en büyük sayı bu oldu
                    satir = i;      //hangi satırın en büyük değere sahip olduğu tutulur
                    for(int z=0;z<boyut+1;z++)  //satırların yeri değişiyor
                    {
                        satirtemp[0][z]  = matris[satir][z];    //satırı tempe atıyorum kaybetmemek için
                        matris[satir][z] = matris[satirsay][z];     //bulunulan satırı ilk satırla değiştiriyorum
                        matris[satirsay][z] = satirtemp[0][z];      //o satırı da tempe atıyorum ki istenen satır istenen yere gelsin
                    }
                    //satirsay=i;     //işlemi gerçekleştirdikten sonra temp satır sayımı da mevcut 
                }
            }
            j++;
        }
        return matris;
    }

    public static void setbaslangic_degerleri(double[][] matris, int boyut)
    {
        Scanner girdi2 = new Scanner(System.in);
        for(int i=0; i<boyut; i++)
        {
            System.out.println("x"+(i+1)+" değişkeninin başlangıç değeri: ");
            matris[i][0] = girdi2.nextDouble();
            matris[i][1] = matris[i][0];
        }
    }

    public static void yeni_deger(double[][] matris,double[][] degerler, int boyut )
    {
        double toplam;
        double katsayi;
        for(int i=0; i<boyut; i++)
        {
            toplam=0.0;
            katsayi = matris[i][i];
            for(int j=0;j<boyut; j++)
            {
                if(i==j)
                {
                    continue;
                }
                else
                {
                    toplam+=(-1)*matris[i][j]*degerler[j][1];
                }
            }
            toplam+=matris[i][boyut];
            toplam/= katsayi;
            degerler[i][0] = degerler[i][1];
            degerler[i][1]=toplam;
        }
    }

    public static double[] hata_hesapla(double[][] degerler, int boyut, double[] hata)
    {
        for(int i=0; i<boyut; i++)
        {
            hata[i] = Math.abs(degerler[i][1]-degerler[i][0]);
        }
        return hata;
    }
    
    public static void yazdir(int boyut,double[][] matris)
    {
        for(int i=0;i<boyut;i++)
        {
            for(int j=0;j<boyut+1;j++)
            {
                System.out.print(matris[i][j] +"   ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        int boyut,iterasyon=1;
        int max_iterasyon=100;
        double epsilon;

        System.out.println("Eklemeli matris boyutunu giriniz (n*(n+1)): ");
        Scanner girdi = new Scanner(System.in);
        boyut = girdi.nextInt();

        double[][] fonksiyonlar = new double[boyut][boyut + 1];
        double[][] degerler = new double[boyut][2];
        double[] hatalar = new double[boyut];

        matris_tanimla(boyut , fonksiyonlar);

        setbaslangic_degerleri(degerler,boyut);

        System.out.println("İstenen hata değerini giriniz (epsilon): ");
        epsilon = girdi.nextDouble();

        matrisi_duzenle(fonksiyonlar, boyut);
        System.out.println("\nDüzenlenmiş matris: ");
        yazdir(boyut, fonksiyonlar);
        System.out.println();

        int hata_tutarlilik = 0;
        while(iterasyon != max_iterasyon)
        {
            if(hata_tutarlilik != boyut)
            {
                yeni_deger(fonksiyonlar, degerler, boyut);
                hata_hesapla(degerler, boyut, hatalar);
                System.out.println(iterasyon + ". iterasyon değerleri: ");
                for(int i=0;i<boyut;i++)
                {
                    System.out.println("x"+(i+1)+"'in yeni değeri: " + degerler[i][1] +"\nMevcut hata değeri: " + hatalar[i] + "\n");
                }   
            }
            else
                break;
            for(int j=0; j<boyut; j++)
            {
                if(hatalar[j] <= epsilon)
                {
                    hata_tutarlilik++;
                }
                else
                {
                    hata_tutarlilik=0;
                    break;
                }
            }
            iterasyon++;
        }
    }
}