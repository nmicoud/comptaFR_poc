package org.tgi.factories;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.tgi.model.MTXXAReport;

public class TgiModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {


		if (MTXXAReport.Table_Name.equals(tableName))
			return MTXXAReport.class;

		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {


		if (tableName.equals(MTXXAReport.Table_Name))
			return new MTXXAReport(Env.getCtx(), Record_ID, trxName);

		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {

		if (MTXXAReport.Table_Name.equals(tableName))
			return new MTXXAReport(Env.getCtx(), rs, trxName);
		return null;
	}

}
