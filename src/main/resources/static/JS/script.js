//const toggleSidebar = () => {
//  if ($(".sidebar").is(":visible")) {
//    $(".sidebar").css("display", "none");
//    $(".content").css("margin-left", "0%");
//  } else {
//    $(".sidebar").css("display", "block");
//    $(".content").css("margin-left", "20%");
//  }
//};

//console.log("asfa");

//alert("load");
function alertMethod(){

alert("message");
}


 function deleteContact(cId){
//        Swal.fire({
//  title: 'Are you sure?',
//  text: "You want to delete this contact !!",
//  icon: 'warning',
//  showCancelButton: true,
//  confirmButtonColor: '#3085d6',
//  cancelButtonColor: '#d33',
//  confirmButtonText: 'Yes, delete it!'
//}).then((result) => {
//  if (result) {
<!--    Swal.fire(-->
<!--      'Deleted!',-->
<!--      'Your file has been deleted.',-->
<!--      'success'-->
<!--    )-->

    window.location="/user/delete/"+cId;
//  }else{
//  Swal("your contact is safe");
//  }
//})
        }