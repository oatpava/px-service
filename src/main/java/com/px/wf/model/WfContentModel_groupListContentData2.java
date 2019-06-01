
package com.px.wf.model;

import com.px.wf.model.WfContentModel;
import static com.google.common.base.CharMatcher.any;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfContentModel_groupListContentData2")
@ApiModel(description = "รายการหนังสือในระบบสารบรรณ")
public class WfContentModel_groupListContentData2 extends WfContentModel {

    private static final long serialVersionUID = 4887211183905992777L;
    
    @Expose @Since(1.0) private List<WfContentModel_groupListContentData2> td;
    
    public WfContentModel_groupListContentData2() {
    }

    public void setTd(List<WfContentModel_groupListContentData2> td) {
        this.td = td;
    }
    
}
