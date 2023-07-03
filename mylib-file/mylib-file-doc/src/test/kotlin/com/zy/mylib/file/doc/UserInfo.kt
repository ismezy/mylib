/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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