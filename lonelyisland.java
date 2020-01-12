/* Nama/NIM: Lukas Kurnia Jonathan/ 13517006 K-03
  Tugas kecil 2 Strategi Algoritma. Lonely Island */
import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Thread;

//kelas lonelyisland menggunakan runnable untuk mengatur stack size
//dalam Java untuk menghandle stack overflow saat deep recursion
public class lonelyisland implements Runnable {
    public static int n,m,r;
    /*n = jumlah pulau, m= jumlah jembatan, r= pulau start*/

    public static void BacaFileEksternal(String namafile, ArrayList<ArrayList<Integer>> adjList,ArrayList<ArrayList<Boolean>> adjListBool)
      {
        ArrayList<Integer> konstruktor = new ArrayList<Integer>();
        ArrayList<Boolean> konstruktorBool = new ArrayList<Boolean>();
        //untuk membuat isi dari adjList menjadi kosong
        try {
          File file = new File (namafile);
          Scanner sc = new Scanner(file);

          lonelyisland.n = sc.nextInt();//mengisi nilai n dengan jumlah pulau
          lonelyisland.m = sc.nextInt();//mengisi nilai m dengan jumlah jembatan
          lonelyisland.r = sc.nextInt();//mengisi nilai r dengan posisi awal pulau

          for(int i=0; i<(lonelyisland.n+1);i++){
          adjList.add(konstruktor); //membuat adj list yang kosong sebanyak pulau+1
          adjListBool.add(konstruktorBool);
          konstruktor = new ArrayList<Integer>();
          konstruktorBool = new ArrayList<Boolean>();
          }

          while(sc.hasNextInt())
          {
            int a = sc.nextInt();
            int b = sc.nextInt();
            adjList.get(a).add(b);
            adjListBool.get(a).add(false);
            // membaca dari file dan memasukkan jembatan dari
            //pulau a ke b ke dalam adjlist
          }
      }
       catch (FileNotFoundException e) { //catch
        //TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    public static void Lonely(ArrayList<ArrayList<Integer>> adjList , ArrayList<ArrayList<Boolean>> adjListBool,
    ArrayList<Integer> solusi, ArrayList<Integer> path,  int Cpulau){
      if(adjList.get(Cpulau).isEmpty()) //Bais
      //Basis dari rekursif yaitu ketika isi adjList dari indeks Cpulau kosong
      {
          if(!solusi.contains(Cpulau))
            {
              solusi.add(Cpulau);
               //memasukkan node/pulau yang merupakan lonely ke dalam list solusi
            }
            System.out.println(path); //Melakukan print enumerasi langkah ke layar
            path.remove(path.size()-1);//Untuk kepentingan enumerasi, menghapus
            //node terakhir sebelum backtrack ke prosedur yang memanggil
        }
      else // Rekurens
      {
        for(int i=0;i<(adjList.get(Cpulau).size());i++)
        //Menyusuri setiap pulau dari starting point
        {
          if(!path.contains(adjList.get(Cpulau).get(i)))
          //Jika pulau belum pernah dikunjungi
          {
            path.add(adjList.get(Cpulau).get(i));//menambahkan pulau yang sudah dikunjungi
            adjListBool.get(Cpulau).set(i,true);//Pulau tersebut di set true(dikunjungi)

            Lonely(adjList,adjListBool,solusi,path,adjList.get(Cpulau).get(i));//Rekurens
          }
          //i==(adjList.get(Cpulau).size() -1)
          else if(i==(adjList.get(Cpulau).size() -1) &&
          !adjListBool.get(Cpulau).contains(true) &&
          path.contains(adjList.get(Cpulau).get(i)))
          {//Jika pulau sudah pernah dikunjungi dan tidak ada lagi pulau lain yang dapat dikunjungi
            if(!solusi.contains(Cpulau))
              {
                solusi.add(Cpulau);//tambahkan pulau lonely ke list solusi
              }
              System.out.println(path);//melakukan print enumerasi langkah ke layar
          };
          //Jika pulau sudah dikunjungi dan masih ada pulau lain,
          //next ke pulau berikutnya
        }
        path.remove(path.size()-1);//menghapus node terakhir
        //sebelum backtrack ke prosedur yang memanggil
      }
      Collections.fill(adjListBool.get(Cpulau), Boolean.FALSE);
      //Di bagian akhir dari loop selalu reset adjListBool menjadi false
    }
    //Class main "dummy"
    //yang sudah ditingkatkan kapasitas size nya
    public static void main(String[] args){
      new Thread(null, new lonelyisland(), "stack_resized", 1<<26).start();
    }
    //Class main yang dijalankan
    public void run() {
        System.out.println( "Lonely Island Simulator");
        System.out.println( "by: Lukas Kurnia Jonathan / 13517006 /K-03");
        System.out.println( "=================================================");
        System.out.println();

        Scanner input = new Scanner(System.in);
        System.out.print("Ketik nama file eksternal dengan ekstensi (.txt): ");
        String namafile = input.nextLine(); //Menerima input nama file

        /*Berbagai tipe data arrayList yang dibutuhkan*/
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Boolean>> adjListBool = new ArrayList<ArrayList<Boolean>>();
        ArrayList<Integer> solusi = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        BacaFileEksternal(namafile,adjList,adjListBool);
        // memasukkan isi file ke dalam listAdj dan variabel penting lain
        long startTime = System.nanoTime(); //Waktu eksekusi algoritma mulai dihitung
        path.add(lonelyisland.r);
        //memasukkan starting point sebagai pulau pertama yang pasti dikunjungi
        System.out.println("Enumerasi langkah dari pulau ke-"+lonelyisland.r+" menuju Lonely Island lain: ");
        Lonely(adjList,adjListBool,solusi,path,lonelyisland.r); //Algoritma utama
        Collections.sort(solusi);
        System.out.println("Lonely Island dari pulau ke-"+lonelyisland.r+" : ");
        System.out.println(solusi); //Print lonely island ke layar

        /*print hasil eksekusi waktu algoritma ke layar*/
        long stopTime = System.nanoTime();
        long elapsedTime = stopTime - startTime;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        System.out.println("Waktu eksekusi: "+ seconds+" detik.");
      }
}
