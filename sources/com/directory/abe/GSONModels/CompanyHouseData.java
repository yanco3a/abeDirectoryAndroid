package com.directory.abe.GSONModels;

import java.util.List;

public class CompanyHouseData {
    public List<Items> items;
    private int items_per_page;
    private String kind;
    private int page_number;
    private int start_index;
    private int total_results;

    public class Address {
        public String address_line_1;
        public String address_line_2;
        public String locality;
        public String postal_code;

        public String toString() {
            return "Address1{address_line_2='" + this.address_line_2 + '\'' + ", locality='" + this.locality + '\'' + ", postal_code='" + this.postal_code + '\'' + ", address_line_1='" + this.address_line_1 + '\'' + '}';
        }
    }

    public class Items {
        public Address address;
        public String company_status;

        public String toString() {
            return "CompanyHouseItems{address=" + this.address + ", company_status='" + this.company_status + '\'' + '}';
        }
    }

    public String toString() {
        return "CompanyHouseData{items=" + this.items + '}';
    }
}
