package cz.cvut.nss.utils.dto;

import java.util.List;

/**
 * Helping result to aggregate ids of filtered results and total count of filtered results.
 *
 * @author jakubchalupa
 * @since 07.04.15
 */
public class IdsAndCountResult {

    private List<Long> list;

    private int count;

    public IdsAndCountResult() {

    }

    public IdsAndCountResult(List<Long> list, int count) {
        this.list = list;
        this.count = count;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
