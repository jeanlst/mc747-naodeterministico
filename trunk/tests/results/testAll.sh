#!/bin/bash
initializeANSI()
{
  esc=""

  blackf="${esc}[30m";   redf="${esc}[31m";    greenf="${esc}[32m"
  yellowf="${esc}[33m"   bluef="${esc}[34m";   purplef="${esc}[35m"
  cyanf="${esc}[36m";    whitef="${esc}[37m"
  
  blackb="${esc}[40m";   redb="${esc}[41m";    greenb="${esc}[42m"
  yellowb="${esc}[43m"   blueb="${esc}[44m";   purpleb="${esc}[45m"
  cyanb="${esc}[46m";    whiteb="${esc}[47m"

  boldon="${esc}[1m";    boldoff="${esc}[22m"
  italicson="${esc}[3m"; italicsoff="${esc}[23m"
  ulon="${esc}[4m";      uloff="${esc}[24m"
  invon="${esc}[7m";     invoff="${esc}[27m"

  reset="${esc}[0m"
}

initializeANSI

cd ../..

for ((  i = 0 ;  i <= 70;  i++  ))
do
	n=$(printf '%02d' $i)
	file="tests/test"$n".pl747";
	fileRes="tests/results/result"$n".res";
	fileOut="tests/results/result"$n".out";
	#echo $n
	if [ -e $fileRes ] ; then
		java -classpath bin/:lib/java-cup-11a.jar Compiler $file 2> $fileOut 1>/dev/null
		r=$(diff $fileOut $fileRes 2>/dev/null)
		if [[ -z $r ]] ; then
			echo $fileRes" ${greenf}OK${reset}"
		else
			echo $fileRes" ${redf}Erro${reset}"
		fi;
	else
		echo "Arquivo de resposta faltando: "$fileRes
	fi;
done
