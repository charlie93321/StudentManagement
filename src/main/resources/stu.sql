select sid id,sname name ,sgender gender, sage age,saddress address,semail email from studentInfo --queryAll--
select sid id,sname name ,sgender gender, sage age,saddress address,semail email from studentInfo where sid=? --queryStuInfoById--
update studentInfo set sname=? , sgender=? , sage=? , saddress=? , semail=? where sid=? --updateStuInfo--
