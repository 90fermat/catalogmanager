$(document).ready(function () {
    const creation = /*[[${creation}]]*/ "product";
    switch (creation) {
        case "catalog":
            $('#catalog').addClass("show active");
            $('#catalog-tab').addClass('active');
            break;
        case "product":
            $('#product').addClass("show active");
            $('#product-tab').addClass('active');
            break;
    }



        const list = /*[(${products})]*/ null;
        console.log(list[0].name);
        if (list != null) {
            debugger;
            for (i = 0; i < list.length; i++) {
                const a = $(document).createElement("a");
                a.addClass('product-drop');
                a.addClass('dropdown-item');
                a.setAttribute("href", "#");
                a.setContent(list[i].name);
                $('#dropdown-content').appendChild(a);
                a.onclick(function () {
                    const li = $(document).createElement("li");
                    const h5 = $(document).createElement("h5");
                    const small = $(document).createElement("small")
                    h5.setContent(list[i].name);
                    small.setContent(list[i].toString())
                    li.addClass('list-group-item');
                    li.appendChild(h5);
                    li.appendChild(small);
                    $('#list-adding-product').appendChild(li)
                });
            }
        }
});