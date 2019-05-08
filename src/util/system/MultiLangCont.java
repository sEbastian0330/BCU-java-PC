package util.system;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import page.MainLocale;
import util.stage.MapColc;
import util.stage.Stage;
import util.stage.StageMap;
import util.unit.Enemy;
import util.unit.Form;

public class MultiLangCont<I, T> {

	public static final MultiLangCont<MapColc, String> MCNAME = new MultiLangCont<>();
	public static final MultiLangCont<StageMap, String> SMNAME = new MultiLangCont<>();
	public static final MultiLangCont<Stage, String> STNAME = new MultiLangCont<>();
	public static final MultiLangCont<Form, String> FNAME = new MultiLangCont<>();
	public static final MultiLangCont<Enemy, String> ENAME = new MultiLangCont<>();
	public static final MultiLangCont<MultiLangFile, VFile> VFILE = new MultiLangCont<>();

	public static String get(Object o) {
		String loc = MainLocale.LOC_CODE[MainLocale.lang];
		if (o instanceof MapColc)
			return MCNAME.get(loc, (MapColc) o);
		if (o instanceof StageMap)
			return SMNAME.get(loc, (StageMap) o);
		if (o instanceof Stage)
			return STNAME.get(loc, (Stage) o);
		if (o instanceof Form)
			return FNAME.get(loc, (Form) o);
		if (o instanceof Enemy)
			return ENAME.get(loc, (Enemy) o);
		return null;
	}

	public static void redefine() {
		String loc = MainLocale.LOC_CODE[MainLocale.lang];
		VFILE.map.get(loc).forEach((mlf, f) -> mlf.reload(f));
	}

	private final Map<String, HashMap<I, T>> map = new TreeMap<>();

	public T get(String loc, I x) {
		T ans = getSub(loc).get(x);
		int lang = 0;
		while (ans == null && lang < 4)
			ans = getSub(MainLocale.LOC_CODE[lang++]).get(x);
		return ans;
	}

	public void put(String loc, I x, T t) {
		if (x == null || t == null)
			return;
		getSub(loc).put(x, t);
	}

	private HashMap<I, T> getSub(String loc) {
		HashMap<I, T> ans = map.get(loc);
		if (ans == null)
			map.put(loc, ans = new HashMap<>());
		return ans;
	}

}
