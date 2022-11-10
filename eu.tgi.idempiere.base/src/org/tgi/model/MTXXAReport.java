package org.tgi.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Util;


/**
 *	Table de travail pour les états
 */
public class MTXXAReport extends X_T_XXA_Report
{
	private static final long serialVersionUID = -3654919519334711143L;

	public MTXXAReport (Properties ctx, int T_XXA_Report_ID, String trxName) {
		super (ctx, T_XXA_Report_ID, trxName);
	}	//	MTXXAReport

	/**
	 * 	MTXXAReport
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MTXXAReport (Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}	//	MTXXAReport

	/** Constructeur pour les reports (XXA_Report...) 
	 * TODO : Faire une méthode create (ctx, instanceID, trxName) et voir pour mettre automatiquement la sessionID (attention aux états lancés depuis un RunAsJob)
	 * */
	public MTXXAReport (Properties ctx, int T_XXA_Report_ID, int instanceID, String trxName)
	{
		super (ctx, T_XXA_Report_ID, trxName);
		setAD_PInstance_ID(instanceID);
	}	//	MTXXAReport

	protected boolean beforeSave (boolean newRecord) {
//		if (Util.isEmpty(getDocumentNo()))
//			set_Value (COLUMNNAME_DocumentNo, "osef"); // Remplir le champ DocumentNo sans quoi, le programme recherche le prochain numéro à affecter en fonction du C_DocType et incrémente la séquence ! (PO.doUpdate(boolean))
		return true;
	}	//	beforeSave

	/** Renvoie la séquence liée à la table T_XXA_Report */
	public static int getSequenceID(String trxName) {
		return DB.getSQLValueEx(trxName, "SELECT AD_Sequence_ID FROM AD_Sequence WHERE Name = ? AND	IsActive = 'Y' AND IsTableID = 'Y' AND IsAutoSequence = 'Y' AND AD_Client_ID = 0", Table_Name);
	}

	/** Renvoie un String qui précise quelles dates sont utilisées dans l'état */
	public static String getDateCriteres(Properties ctx, int clientID, Timestamp dateFrom, Timestamp dateTo) {
		if (dateFrom != null && dateTo != null)
			return Msg.getMsg(ctx, "XXA_CritereDateBetween", new Object[] {formatDate(ctx, clientID, dateFrom), formatDate(ctx, clientID, dateTo)});
		else if (dateFrom != null && dateTo == null)
			return Msg.getMsg(ctx, "XXA_CritereDateFrom", new Object[] {formatDate(ctx, clientID, dateFrom)});
		else if (dateFrom == null && dateTo != null)
			return Msg.getMsg(ctx, "XXA_CritereDateUntil", new Object[] {formatDate(ctx, clientID, dateTo)});
		else
			return Msg.getMsg(ctx, "XXA_NoCritereDate");		
	}

	public static int getDocTypeForBankStatement(Properties ctx) {
		return MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_BankStatement)[0].getC_DocType_ID();
	}

	public static int getDocTypeForAllocation(Properties ctx) {
		return MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_PaymentAllocation)[0].getC_DocType_ID();
	}

	

	/** Formatte la date passée en paramètre selon le format défini */
	public static String formatDate (Properties ctx, int clientID, Object value)
	{
		return formatDate(ctx, clientID, value, "");
	}

	/** Formatte la date passée en paramètre selon le format défini */
	public static String formatDate (Properties ctx, int clientID, Object value, String format)
	{
		if (Util.isEmpty(format))
			format = "dd/MM/yyyy";

		Locale locale = MClient.get(ctx, clientID).getLanguage().getLocale();
		return new SimpleDateFormat(format, locale).format(value);
	}


}	//	MTXXAReport