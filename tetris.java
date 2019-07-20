import java.util.*;
import java.util.concurrent.TimeUnit;
//import mobile.*;
// Other imports go here, Do NOT change the class name

public class Main
{
  static char board [][] = new char [20][20];
  static char board_backup[][] =new char [20][20];
  static int border[]=new int [20];
  static int commit;
  static Stack <Integer> undo=new Stack<Integer>();
  static Stack <Integer> redo=new Stack<Integer>();

  public static void getBoard()
  {
    int i,j;
    for(i=0;i<20;i++)
    {
      for(j=0;j<20;j++)
      {
        System.out.print(board[i][j]);
      }
       System.out.println();
    }
  }
   public static void clearBoard()
  {
    for(int i=0;i<20;i++)
    {
      for(int j=0;j<20;j++)
      {
        board[i][j] = board_backup[i][j];
      }
       
    }
   /* for(int i=0;i<20;i++)
    {
      board[i][19]='*';
      board[19][i]='*';
      board[i][0]='*';
    }*/
  }
    
  public static void clearBoardBackup()
  {
    for(int i=0;i<20;i++)
    {
      for(int j=0;j<20;j++)
      {
        board_backup[i][j]=' ';
      }
       
    }
    for(int i=0;i<20;i++)
    {
      board_backup[i][19]='*';
      board_backup[19][i]='*';
      board_backup[i][0]='*';
    }
  }
   public static void setBoard(int coordinates0[][])
  {
    for(int i=0;i<4;i++)
    {
        board[coordinates0[i][0]][coordinates0[i][1]]='#';
    }
   
  }

  static void clearConsole()
  {
      System.out.print("\033[H\033[2J");

  }


  static void delay()
  {
   try
    {
        Thread.sleep(100);
    }
    catch(InterruptedException ex)
    {
      Thread.currentThread().interrupt();
    }

  }

  static void setBoardBackup()
  {
    int i,j;
    for(i=0;i<20;i++)
    {
      for(j=0;j<20;j++)
      {
        board_backup[i][j]=board[i][j];
      }
    }
  }

  static boolean rightBoundary(int coordinates0[][])
  {
    int i,j;
    for(i=0;i<4;i++)
    {
      if(board[coordinates0[i][0]][coordinates0[i][1]+1]!=' ')
      {
        for(j=0;j<4;j++)
        {
          if(coordinates0[i][1]+1==coordinates0[j][1])
          {
            break;
          }
        }
        if(j==4)
        {
          return true;
        }
      }
    }
    return false;
  }

  static boolean leftBoundary(int coordinates0[][])
  {
    int i,j;
    for(i=0;i<4;i++)
    {
      if(board[coordinates0[i][0]][coordinates0[i][1]-1]!=' ')
      {
        for(j=0;j<4;j++)
        {
          if(coordinates0[i][1]-1==coordinates0[j][1])
          {
            break;
          }
        }
        if(j==4)
        {
          return true;
        }
      }
    }
    return false;
  }


  static boolean downBoundary(int coordinates0[][])
  {
    int i,j;
    for(i=0;i<4;i++)
    {
      if(board[coordinates0[i][0]+1][coordinates0[i][1]]!=' ')
      {
        for(j=0;j<4;j++)
        {
          if(coordinates0[i][0]+1==coordinates0[j][0])
          {
            break;
          }
        }
        if(j==4)
        {
          return true;
        }
      }
    }
    return false;
  }

  static void setBorder(int coordinates0[][])
  {
    for(int i=0;i<4;i++)
    {
      border[coordinates0[i][0]]++;
      if(border[coordinates0[i][0]]==18)
      {
        System.out.println("1 row completed");
      }
    }
  }

  public static void main(String[] args)
  {
    Scanner in=new Scanner(System.in);
  clearBoardBackup();
  for(int i=0;i<20;i++)
  {
           int n = (int)(Math.random()*100)%5;
        Shapes s=new Stick();
       
          switch(n)
        {
          case 0:
          {
             s = new Stick();
              s.getShape();
            break;
          }
          case 1:
          {
          s = new Square();
             s.getShape();
            break;
          }
          case 2:
          {
             s = new L();
              s.getShape();
            break;
          }
          case 3:
          {
             s = new Z();
             s.getShape();
            break;
          }

          case 4:
          {
             s = new T();
             s.getShape();
            break;
          }
        }
   
    // InputDetector detector = new InputDetector();
    //     detector.start();
    while(true)
    {
      //  if (detector.getInput().equals("d"))
      //   {
      //       break;
      //   }
    
      int x=in.nextInt();
      if(x==3&&!rightBoundary(s.coordinates0))
      {
      s.right();
      s.down();
      undo.push(3);
      }
      if(x==1&&!leftBoundary(s.coordinates0))
      {
      s.left();
      s.down();
      undo.push(1);
      }
      if(x==5)
      {
        s.clockwiseRotate();
        s.down();
        undo.push(5);
      }
      if(x==2){
        s.down();
        undo.push(2);
      }
      if(x==0&&!undo.isEmpty())
      {
        int getUndo=undo.peek();
        undo.pop();
        if(getUndo==3)
        {
          s.up();
          s.left();
         
        }
        else if(getUndo==1)
        {
          s.up();
          s.right();
        }
        else if(getUndo==2){
          s.up();
        }
        else if(getUndo==5)
        {
          s.up();
          s.anticlockwiseRotate();
        }
      }
    clearConsole();
    clearBoard();
    setBoard(s.coordinates0);
    getBoard();
    
  //  s.down();
      if(downBoundary(s.coordinates0))
      {
      setBoardBackup();
      setBorder(s.coordinates0);
        break;
      }
      else
      //s.down();
    delay();
    }
  //System.out.print("\033[H\033[2J");
  }
   
  
  }

}

// public class InputDetector implements Runnable
// {
//     private Thread thread;
//     private String input;
//     private Scanner scan;

//     public InputDetector()
//     {
//         input = "";
//         scan = new Scanner(System.in);
//     }

//     public void start()
//     {
//         thread = new Thread(this);
//         thread.start();
//     }

//   //  @Override
//     public void run()
//     {
//         while (!(input.equals("exit")))
//         {
//             input = scan.nextLine();
//         }
//     }

//     public String getInput()
//     {
//         return input;
//     }
// }

 abstract class Shapes{
  int coordinates0 [][]=new int [4][2];
  int coordinates1 [][]=new int [4][2];
  int coordinates2 [][]=new int [4][2];
  int coordinates3 [][]=new int [4][2];

  int curr_version;
    
  Shapes()
  {
    curr_version=1;
  }
  abstract void getVersion1();
  abstract void getVersion2();
  abstract void getVersion3();
  abstract void getVersion4();
  abstract void clockwiseRotate();
  abstract void anticlockwiseRotate();
  void getShape()
  {
    getVersion1();
    randomStart();
  }


  void  up()
  {

    coordinates0[0][0]-=1;

    coordinates0[1][0]-=1;

    coordinates0[2][0]-=1;
    
    coordinates0[3][0]-=1;
   
  }

  boolean down()
  {
    if(coordinates0[0][0]==18||coordinates0[1][0]==18||coordinates0[2][0]==18||coordinates0[3][0]==18)
    {
      return true;
    }
    else
    {
    coordinates0[0][0]+=1;

    coordinates0[1][0]+=1;

    coordinates0[2][0]+=1;
    
    coordinates0[3][0]+=1;
    }
    return false;
  }

    void right()
  {
    if(coordinates0[0][1]==18||coordinates0[1][1]==18||coordinates0[2][1]==18||coordinates0[3][1]==18)
    {

    }
    else{
    coordinates0[0][1]+=1;

    coordinates0[1][1]+=1;

    coordinates0[2][1]+=1;
    
    coordinates0[3][1]+=1;
    }
  }

      void left()
  {

    if(coordinates0[0][1]==1||coordinates0[1][1]==1||coordinates0[2][1]==1||coordinates0[3][1]==1)
    {

    }
    else{
    coordinates0[0][1]-=1;

    coordinates0[1][1]-=1;

    coordinates0[2][1]-=1;
    
    coordinates0[3][1]-=1;
    }
  }
  
  void randomStart()
  {
    int i=(int)(Math.random()*100)%16;
     for(int j=0;j<4;j++)
     {
       coordinates0[j][1]+=i;
     }
  }
  
}


class Stick extends Shapes{


  //int coordinates4 [][]=new int [4][2];


  void getVersion1()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=2;
    coordinates0[1][1]=1;

    coordinates0[2][0]=3;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=4;
    coordinates0[3][1]=1;
   // super.randomStart();
  }

  void getVersion2()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=1;
    coordinates0[2][1]=3;
    
    coordinates0[3][0]=1;
    coordinates0[3][1]=4;
  
  }

   void getVersion3()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=2;
    coordinates0[1][1]=1;

    coordinates0[2][0]=3;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=4;
    coordinates0[3][1]=1;
   
  }

  void getVersion4()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=1;
    coordinates0[2][1]=3;
    
    coordinates0[3][0]=1;
    coordinates0[3][1]=4;
     }

  void clockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      Stick temp=new Stick();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
      Stick temp=new Stick();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==3)
    {
      Stick temp=new Stick();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
      Stick temp=new Stick();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }

  } 


  void anticlockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      Stick temp=new Stick();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
      Stick temp=new Stick();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==3)
    {
      Stick temp=new Stick();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
      Stick temp=new Stick();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }

  } 

}





class Square extends Shapes{

  

  void getVersion1()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=2;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=2;
    //super.randomStart();
  }

  void getVersion2()
  {
   getVersion1();
   }

   void getVersion3()
  {
   getVersion1();
   }

  void getVersion4()
  {
    getVersion1();
  }

  void clockwiseRotate()
  {
    
  } 

  void anticlockwiseRotate()
  {
    
  } 

}



class L extends Shapes{


  void getVersion1()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=2;
    coordinates0[1][1]= 1;

    coordinates0[2][0]=3;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=2;
  //  super.randomStart();
  }

  void getVersion2()
  {
    coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=1;
    coordinates0[2][1]=3;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=1;
    
   }

   void getVersion3()
  {
      coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=2;
    
   }

  void getVersion4()
  {
    
    coordinates0[0][0]=1;
    coordinates0[0][1]=3;

    coordinates0[1][0]=2;
    coordinates0[1][1]=1;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=3;
    
  }

  void clockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      L temp=new L();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
       L temp=new L();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==3)
    {
       L temp=new L();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {

      getVersion4();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
       L temp=new L();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {

      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }


  }
  
  
  void anticlockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==3)
    {
      L temp=new L();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==4)
    {
       L temp=new L();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==1)
    {
       L temp=new L();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {

      getVersion4();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==2)
    {
       L temp=new L();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {

      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }


  }

}




class T extends Shapes{

  

  void getVersion1()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=2;

    coordinates0[1][0]=2;
    coordinates0[1][1]= 1;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=3;
//    super.randomStart();
  }

  void getVersion2()
  {
    coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=2;
    coordinates0[1][1]=1;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=1;
    
   }

   void getVersion3()
  {
      coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=1;
    coordinates0[2][1]=3;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=2;
    
   }

  void getVersion4()
  {
    
    coordinates0[0][0]=2;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]=2;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=2;
    
  }
  void clockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      T temp=new T();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
      T temp=new T();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }

    else if(curr_version==3)
    {
      T temp=new T();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
      T temp=new T();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }
  }

  void anticlockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      T temp=new T();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
      T temp=new T();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }

    else if(curr_version==3)
    {
      T temp=new T();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
      T temp=new T();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }

  } 

}



class Z extends Shapes{

  void getVersion1()
  {
     coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=1;
    coordinates0[1][1]= 2;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=3;
  }

  void getVersion2()
  {
    coordinates0[0][0]=1;
    coordinates0[0][1]=2;

    coordinates0[1][0]=2;
    coordinates0[1][1]=2;

    coordinates0[2][0]=2;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=1;
    
   }

   void getVersion3()
  {
      coordinates0[0][0]=1;
    coordinates0[0][1]=1;

    coordinates0[1][0]=2;
    coordinates0[1][1]=1;

    coordinates0[2][0]=2;
    coordinates0[2][1]=2;
    
    coordinates0[3][0]=3;
    coordinates0[3][1]=2;
    
   }

  void getVersion4()
  {
    
    coordinates0[0][0]=1;
    coordinates0[0][1]=2;

    coordinates0[1][0]=1;
    coordinates0[1][1]=3;

    coordinates0[2][0]=2;
    coordinates0[2][1]=1;
    
    coordinates0[3][0]=2;
    coordinates0[3][1]=2;
  
  }

 void clockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      Z temp=new Z();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
       Z temp=new Z();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==3)
    {
       Z temp=new Z();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
       Z temp=new Z();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }

  } 

  void anticlockwiseRotate()
  {
    int x=coordinates0[0][0]-1;
    int y=coordinates0[0][1]-1;
    //x=0;
    //y=0;
  

    if(curr_version==1)
    {
      Z temp=new Z();
      temp.getVersion4();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion4();
       for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=2;
    }
     
    }
    else if(curr_version==2)
    {
       Z temp=new Z();
      temp.getVersion1();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion1();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=3;
    }
    }
    else if(curr_version==3)
    {
       Z temp=new Z();
      temp.getVersion2();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion2();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=4;
    }
    }
    else if(curr_version==4)
    {
       Z temp=new Z();
      temp.getVersion3();
        int m=0;
    for(int i=0;i<4;i++)
    {
      if(m<temp.coordinates0[i][1])
      {
        m=temp.coordinates0[i][1];    
      }
    }
    if(m+y<19)
    {
      getVersion3();
      for(int i=0;i<4;i++)
      {
        coordinates0[i][0]+=x;
        coordinates0[i][1]+=y;
        
      }
      curr_version=1;
    }
    }

  } 


}