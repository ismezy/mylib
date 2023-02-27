package com.zy.mylib.file.doc

import fr.opensagres.xdocreport.document.images.IImageProvider
import fr.opensagres.xdocreport.template.annotations.FieldMetadata
import fr.opensagres.xdocreport.template.annotations.ImageMetadata

class UserInfo {
  var name: String? = null
  var sex: String? = null
  var age: Int? = null
  var photo: IImageProvider? = null
    @FieldMetadata( images = [ ImageMetadata( name = "photo" ) ], description="Photo of developer"  )
    get

}