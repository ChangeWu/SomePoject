package com.bobinfo.projecttimeviewers.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;

/**
 * bitmap与base64编码字符串互转
 * @author Change
 * @date 2013-9-23[下午2:09:48]
 */
public class BitmapFileUtils {
	public static String getBitmapStrBase64(Bitmap bitmap){ 
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); 
	    byte[] bytes  = baos.toByteArray(); 
	    return Base64.encode(bytes); 
	}
	
	/**
	 * @param in
	 * @return
	 */
	public static String getBitmapStrBase64(InputStream in){
		byte buf[] = new byte[1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int length = 0;
		try {
			while((length = in.read())!=-1){
				bout.write(buf, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encode(bout.toByteArray());
	}
	
	public static String file2Base64String(File file){
		String bitmapStr = "";
		try {
			FileInputStream inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];  
		        int leng;  
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			       while((leng=inputStream.read(bytes)) != -1){  
			             baos.write(bytes, 0, leng);  
			       }  					 
			bitmapStr = Base64.encode(baos.toByteArray());			
			inputStream.close();
			baos.close();
		} catch (Exception e) {
			
		}
		return bitmapStr;
	}
	
	
//	public static String[] pinyin = { "a", "ai",  "an", "ang", "ao", "ba", "bai", "ban", "bang",   
//		"bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin",   
//		"bing", "bo", "bu", "ca", "cai", "can", "cang", "cao",  "ce",   
//		"ceng", "cha", "chai", "chan", "chang", "chao", "che",  "chen",   
//		"cheng", "chi", "chong", "chou", "chu", "chuai", "chuan",
//		"chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", 
//		"cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao",   
//		"de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong",   
//		"dou", "du", "duan", "dui", "dun", "duo", "e", "en", "er",  "fa",   
//		"fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", 
//		"gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng",  "gong",   
//		"gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo",   
//		"ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng",   
//		"hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun",  
//		"huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing",   
//		"jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan",   
//		"kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua",    
//		"kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan",    
//		"lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang",   
//		"liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv",   
//		"luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao",   
//		"me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min",    
//		"ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang",   
//		"nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao",   
//		"nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue",   
//		"nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen",   
//		"peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
//		"qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", 
//		"qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao",    
//		"re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui",   
//		"run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen",    
//		"seng", "sha", "shai", "shan", "shang", "shao", "she", "shen",    
//		"sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang",   
//		"shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui",    
//		"sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng",
//		"ti", "tian", "tiao", "tie", "ting", "tong", "tou",  "tu", "tuan",   
//		"tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen",   
//		"weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie",   
//		"xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",   
//		"yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong",   
//		"you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang",   
//		"zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan",   
//		"zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou",    
//		"zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo",   
//		"zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo" };
}
