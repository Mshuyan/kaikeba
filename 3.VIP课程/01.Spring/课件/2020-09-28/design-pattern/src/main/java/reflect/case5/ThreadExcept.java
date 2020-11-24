package reflect.case5;

public	class ThreadExcept implements Runnable  {
		public void run()  {  
			throw new RuntimeException("exception ");  
		}

		public static void main(String[]  args)  {
			new  Thread (new  ThreadExcept()).start();
			try  {
				int x=Integer.parseInt(args[0]);
				Thread.sleep (x);
				System.out.print("main");
			} catch (Exception e)  {
			}
		}
	}