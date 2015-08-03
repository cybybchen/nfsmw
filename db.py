#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime
import os
import sys
import time
import MySQLdb
import string
def show():
    try:
	NEWDATABASE='userdbtest'
	OLDDATABASE='userdbtest2'
	
        conn=MySQLdb.connect(host="localhost",port=3306,user="root",passwd="Passw0rd",db="userdbtest")
        cursor=conn.cursor();
	cursor.execute("set names utf8")
	cursor.execute("show tables")
	ALLTABLES=cursor.fetchall()
	sqlcmd = open('./sqlcmd.txt','w')
	difftxt = open('./diffdb.txt','w')		
	for TABLE in ALLTABLES:
	 	STR=TABLE[0][0:4]	
		DICT=TABLE[0]
	        if STR != '':
			cursor.execute("desc %s" % (DICT))
			COLUMNS=cursor.fetchall()
                        TITLE_COL="TableName"
                        A_COL="'NEW' AS TableName"
                        B_COL="'LIVE' AS TableName"
			GRP_COL=""
			for COLUMN in COLUMNS:	
				COL=COLUMN[0]
				TITLE_COL=TITLE_COL+', '+COL
				A_COL=A_COL+', '+'a.'+COL
				B_COL=B_COL+', '+'b.'+COL
				if GRP_COL == "":
					GRP_COL=GRP_COL+COL
				else:
					GRP_COL=GRP_COL+', '+COL
			cconn=MySQLdb.connect(host="localhost",port=3306,user="root",passwd="Passw0rd")
		        ccursor=conn.cursor();
			TABLENAME="TableName %s " % (GRP_COL)
			sqlcmd.write('##'+DICT+'\n')
			difftxt.write('##'+DICT+'\n')
			difftxt.write(TABLENAME+'\n')
			ccursor.execute("set names utf8")
			sql = "SELECT %s FROM (SELECT %s FROM %s.%s AS a UNION ALL SELECT %s FROM %s.%s AS b) AS dummy GROUP BY %s HAVING COUNT(*) = 1;" % (TITLE_COL,A_COL,NEWDATABASE,DICT,B_COL,OLDDATABASE,DICT,GRP_COL)
			print sql
			try:
				ccursor.execute(sql);
			except Exception,e:
				print e
			ALLLINE=ccursor.fetchall()
		#	print (ALLLINE)
			GRPARRAY=GRP_COL.split(', ')
		#	print (GRPARRAY)
			num=0
			ARRAY=[]
			OLDARRAY=[]	
			if ALLLINE != "":
				for LINES in ALLLINE:	
					num=num+1
					print "num = `%s`" % (num)
					dtxt=""
					for LINE in LINES:
						dtxt=dtxt+' ' +str(LINE)
					difftxt.write(dtxt+'\n')
					ARRAY=[str(x) for x in LINES]
					
					if len(OLDARRAY) > 0:
						print "-----------+%s" % (len(ARRAY))
						print "-----------+%s" % (len(OLDARRAY))
						if ARRAY[0] != OLDARRAY[0]:
							if OLDARRAY[0] == 'NEW':
								sqlcmd.write(create_updatecmd(OLDARRAY,ARRAY,GRPARRAY,DICT)+'\n')
								print "updata oldarray cmd"
							else:
								sqlcmd.write(create_updatecmd(ARRAY,ARRAY,GRPARRAY,DICT)+'\n')
								print "update array cmd"
							ARRAY=[]
						else:
							if ARRAY[0] == 'LIVE':
								print "--------------------------------------------------------------------------------------------"
								print "Fatal error ! In `%s`" % (DICT)
								difftxt.write("------------------------------------------------------------------------------------------"+'\n')
								difftxt.write("Fatal error ! In `%s`" % (DICT)+'\n')
								#break
							else:
                                                		sqlcmd.write(create_insertcmd(OLDARRAY,GRPARRAY,DICT)+'\n')
								print "insert oldarray cmd"
					else:	
						print len(ALLLINE)
						print num
						if num == len(ALLLINE):
							if ARRAY[0] == 'NEW':
                                                		sqlcmd.write(create_insertcmd(ARRAY,GRPARRAY,DICT)+'\n')
								print "insert array cmd"
							else:
								print "--------------------------------------------------------------------------------------------"
								print "Fatal error ! In `%s`" % (DICT)
								print "Error! two live"
								difftxt.write("------------------------------------------------------------------------------------------"+'\n')
								difftxt.write("Fatal error ! In `%s`" % (DICT)+'\n')
								#break
					OLDARRAY=ARRAY
				#	print "OLDARRAY LEN = =========== %s " % (len(OLDARRAY))
			ccursor.close()
			cconn.close() 
        sqlcmd.close()
        difftxt.close()
        cursor.close()
        conn.close()
    except Exception,e:
        print e
        conn.close();
	
def create_updatecmd(oldarray,array,grparray,_dict):
	ACMD=""
        BCMD=""
        CMD=""
        for i in range(len(oldarray)-1):
        	if i < len(grparray)-1:
       			flag=','
        	else:
                	flag=' '
        	ACMD=ACMD+'%s=\'%s\'' % (grparray[i],oldarray[i+1]) +'%s' % (flag)
       	 	if i < len(oldarray)-2:
                	flag=''
			_flag='and'
        	else:
                	flag=';'
			_flag=''
        	BCMD=BCMD+'%s=\'%s\' %s ' % (grparray[i],array[i+1],_flag) +'%s' % (flag)
        CMD='UPDATE %s ' % (_dict) +'SET'+ ' %s ' % (ACMD) + 'WHERE %s ' % (BCMD)
	return CMD
def create_insertcmd(array,grparray,_dict):
	ACMD=""
        BCMD=""
        CMD=""
        for i in range(len(array)-1):
                if i < len(grparray)-1:
			flag=','
                else:
                        flag=' '
		ACMD=ACMD+'`%s`' % (grparray[i]) +'%s' % (flag)
                if i < len(array)-2:
                        flag=','
                else:
                        flag=' '
                BCMD=BCMD+'\'%s\'' % (array[i+1]) +'%s' % (flag)
        CMD='INSERT INTO %s' % (_dict) +'( %s )' % (ACMD) + ' VALUES ( %s ) ' % (BCMD) + ';'
	return CMD
show()
         
