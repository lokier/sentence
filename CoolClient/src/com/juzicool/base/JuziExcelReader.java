

package com.juzicool.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class JuziExcelReader {
	final File mOutput;

	//分类	点评	鉴赏标签	推荐使用场景

	
	private static final int START_ROW = 1;
	private static final int START_COLUMN = 1;

	private Workbook  mWorkBook = null;
	private Sheet mSheet = null;
	private final int mSheetIndex;
	private int mCurrentReadRow = START_ROW;
	
	public JuziExcelReader(File file) {
		this(file,0);
	}

	public JuziExcelReader(File file,int sheetIndex) {
		mOutput = file;
		mSheetIndex = sheetIndex;
	}

	public void prepare() throws Exception {
		//2:创建工作簿
		mWorkBook=Workbook.getWorkbook(mOutput);
		//3:创建sheet,设置第二三四..个sheet，依次类推即可
		mSheet=mWorkBook.getSheet(mSheetIndex);
		mCurrentReadRow = START_ROW;
	}
	
	public void close() throws IOException {
		//写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
		if(mWorkBook  == null) {
			return;
		}
		//最后一步，关闭工作簿
		mWorkBook.close();
		mWorkBook =null;
		mSheet = null;
	}
	
	//private static final String[] TITLE = {"句子","出自","作者","分类","点评","鉴赏","推荐使用场景"};

	public Juzi nextJuzi() {
		final int row = this.mCurrentReadRow++;
		
		Juzi juzi= new Juzi();

		try {
			juzi.content = getString(mSheet.getCell(START_COLUMN+0,row));
			juzi.from = getString(mSheet.getCell(START_COLUMN+1,row));
			juzi.author = getString(mSheet.getCell(START_COLUMN+2,row));
			juzi.category = getString(mSheet.getCell(START_COLUMN+3,row));
			juzi.remark = getString(mSheet.getCell(START_COLUMN+4,row));
			juzi.tags = getString(mSheet.getCell(START_COLUMN+5,row));
			juzi.applyDesc = getString(mSheet.getCell(START_COLUMN+6,row));
		}catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

		if(StringUtils.isEmpty(juzi.content)) {
			return null;
		}
		
		return juzi;
	
	}
	
	private static String getString(Cell cell) {
		return cell == null ? null:cell.getContents();
	}

	/**
	 * 读句子，
	 * @param size
	 * @return null, 没有的话返回null
	 */
	public List<Juzi> nextJuzi(int size){
		
		ArrayList<Juzi> ret = new ArrayList<>(size);
		
		while(ret.size() < size) {
			Juzi juzi = nextJuzi();
			if(juzi == null) {
				break;
			}
			ret.add(juzi);
		}
		
		if(ret.size() <1) {
			return null;
		}
		return ret;
	}
	
	
}
