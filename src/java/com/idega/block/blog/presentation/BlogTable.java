package com.idega.block.blog.presentation;



import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.PresentationObjectContainer;
import com.idega.presentation.Table;



public class BlogTable extends PresentationObjectContainer {



  public static final int SINGLE_FILE_LAYOUT = 1;

  public static final int BLOG_SITE_LAYOUT = 2;

  public static final int BLOG_PAPER_LAYOUT = 3;

  private int iLayout = BLOG_SITE_LAYOUT;

  private int iObjectCount = 0;

  private int iUndividedCount = 1;

  private int iDividedColumnCount = 2;

  private int iPlannedObjectCount = 1;



  private int linejump = 1;

  protected int tableRows;

  protected int tableColumns;

  protected int rowToAddIn;

  protected int colToAddIn;

	private int cellPadding = 0;

	private int cellSpacing = 0;

	private String firstColor = null;

	private String secondColor = null;

	private String color = "";



  private String sAlign = "left";

	boolean zebracolored = false,usecolor = false;;



  private Table table = null;



  public BlogTable(){

    iLayout = BLOG_SITE_LAYOUT;

  }



  public BlogTable(int iLayout){

    this.iLayout = iLayout ;

  }



  public BlogTable(int iLayout,int iNumberOfObjects){

    this.iLayout = iLayout ;

    iPlannedObjectCount = iNumberOfObjects;

  }



	public BlogTable(int iLayout,int cellPadding,int cellSpacing,String firstColor,String secondColor){

    this.iLayout = iLayout ;

		this.cellPadding = cellPadding;

		this.cellSpacing = cellSpacing;

		this.firstColor = firstColor;

		this.secondColor = secondColor;

		if(firstColor != null ){

			if(secondColor != null)

			  zebracolored =true;



			color = firstColor;

			usecolor = true;

		}



  }



  private void init(){

    tableRows = 1;

    tableColumns = 1;

    rowToAddIn = 1;

    colToAddIn = 1;

    if(iLayout == BLOG_SITE_LAYOUT){

      int rows = 1;

      // calculate rows needed

      if(iPlannedObjectCount > iDividedColumnCount ){

        int left = iPlannedObjectCount-iUndividedCount;

        rows = iUndividedCount + (left/iDividedColumnCount);

        if((left%iDividedColumnCount)>0)

          rows++;

      }

      table = new Table(iDividedColumnCount,rows);

    }

    else{

      table = new Table(1,iPlannedObjectCount);

    }

    table.setWidth("100%");

		table.setCellpadding(cellPadding);

		table.setCellspacing(cellSpacing);

    table.setResizable(true);

    //table.setBorder(1);

  }



  // Stilla t�flu vegna ��kve�innar st�r�ar

  private void finite(){

    if(table != null){

      for (int i = 1; i <= table.getColumns(); i++) {

        int percent = 100/iDividedColumnCount ;

        table.setWidth(i,percent+"%");

        table.setColumnVerticalAlignment(i,"top");

      }

    }

  }



  public void add(PresentationObject Mo,boolean useSetDivison,String sAlign){

    if(table == null)

      init();



    if(useSetDivison && iLayout == BLOG_SITE_LAYOUT){

      if(iObjectCount < iUndividedCount){

        table.mergeCells(1,rowToAddIn ,2,rowToAddIn );

        table.add(Mo,colToAddIn,rowToAddIn);

        table.setVerticalAlignment(colToAddIn,rowToAddIn,"top");

				if(usecolor)

				table.setColor(colToAddIn,rowToAddIn,color);

        iObjectCount++;

        rowToAddIn++;

      }

      else if(colToAddIn < iDividedColumnCount){

        table.add(Mo,colToAddIn,rowToAddIn);

        table.setVerticalAlignment(colToAddIn,rowToAddIn,"top");

				if(usecolor)

				table.setColor(colToAddIn,rowToAddIn,color);

        colToAddIn++;

        iObjectCount++;

      }

      else{

        table.add(Mo,colToAddIn,rowToAddIn);

        table.setVerticalAlignment(colToAddIn,rowToAddIn,"top");

				if(usecolor)

				table.setColor(colToAddIn,rowToAddIn,color);

        colToAddIn--;

        rowToAddIn++;

        iObjectCount++;

      }

    }

    else{

      if(colToAddIn <= iDividedColumnCount  && colToAddIn > iUndividedCount){

        rowToAddIn++;

      }

      colToAddIn = 1;

      table.mergeCells(1,rowToAddIn ,2,rowToAddIn );

      table.setAlignment(1,rowToAddIn,sAlign);

			if(usecolor)

				table.setColor(colToAddIn,rowToAddIn,color);

      table.add(Mo,1,rowToAddIn);

      rowToAddIn++;

      iObjectCount++;

    }



		if(zebracolored){

		  if(color.equals(firstColor))

				color = secondColor;

			else

				color = firstColor;

		}



  }



  public void add(PresentationObject Mo){

   add(Mo,false,sAlign);

  }



  public void add(PresentationObject Mo,String sAlign){

   add(Mo,false,sAlign);

  }



  public void main(IWContext iwc){

    finite();

    super.add(table);

  }



} // Class ListTable
