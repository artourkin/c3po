package template_configurator;

import org.simpleframework.xml.*;

@Root
public class property {
	@Element
	String name;
	@Element
	String render_type;


	public String getRender_type() {
		return render_type;
	}

	public void setRender_type(String render_type) {
		this.render_type = render_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public property(String name, String render_type) {
		super();
		this.name = name;
		this.render_type = render_type;
	}
	public property(){}
	@Override
	public int hashCode() {
		int result=0;
		result+=name.length();
		result+=render_type.length();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null)
			return false;
		if (obj.getClass()!=getClass())
			return false;
		property that=(property) obj;
		if (name.equals(that.name) && (render_type.equals(that.render_type)))
			return true;
		return false;
	}
}