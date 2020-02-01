import editorImage from '@/components/Tinymce'
import plugins from '@/components/editContainer/plugins'
import toolbar from '@/components/editContainer/toolbar'
import goodsApi from '@/api/shop/goods'
import {getAttrBy} from '@/api/shop/attrVal'
import {getCategories} from '@/api/shop/category'
import {getApiUrl} from '@/utils/utils'
import {getToken} from '@/utils/auth'
import goodsSku from "../../../api/shop/goodsSku";

export default {
  components: {editorImage},
  props: {
    id: {
      type: String,
      default: function () {
        return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
      }
    },
    value: {
      type: String,
      default: ''
    },
    toolbar: {
      type: Array,
      required: false,
      default() {
        return []
      }
    },
    menubar: {
      type: String,
      default: 'file edit insert view format table'
    },
    height: {
      type: Number,
      required: false,
      default: 360
    }
  },
  data() {
    return {
      spec:'one',
      specs:[],
      specDialogFormVisible:false,
      skuForm:{},
      attrKeySel:'',
      attrKeyList:[],
      attrValList:[],
      showAddAttrKey:false,
      attrKeyForm:{attrName:''},
      attrValForm:{attrVal:''},
      attrValSel:'',
      attrValListSel:[],
      tags:[
      ],
      form: {
        pic:'',
        gallery:[],
        idCategory:'',
        isHot:false,
        isNew:false
      },
      skuList:[],
      uploadUrl: '',
      uploadFileId: '',
      uploadHeaders: {
        'Authorization': ''
      },
      idGoods: '',
      active: 0,
      categories: [],
      tinymceId: 'tinymceId',
      fullscreen: false,
      languageTypeList: {
        'en': 'en',
        'zh': 'zh_CN'
      },
      galleryList: [],
      apiUrl: getApiUrl()
    }
  },
  computed: {
    language() {
      return this.languageTypeList[this.$store.getters.language]
    },
  },

  watch: {
    value(val) {
      if (!this.hasChange && this.hasInit) {
        this.$nextTick(() =>
          window.tinymce.get(this.tinymceId).setContent(val || ''))
      }
    },
    language() {
      this.destroyTinymce()
      this.$nextTick(() => this.initTinymce())
    }
  },
  mounted() {
    this.init()
    this.initTinymce()
  },
  activated() {
    this.initTinymce()
  },
  deactivated() {
    this.destroyTinymce()
  },
  destroyed() {
    this.destroyTinymce()
  },
  methods: {
    init() {
      this.uploadUrl = getApiUrl() + '/file'
      this.uploadHeaders['Authorization'] = getToken()
      this.idGoods = this.$route.query.id
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      if(this.idGoods) {
        goodsApi.get(this.idGoods).then(response => {
          this.form = response.data.goods
          this.idGoods = this.form.id
          this.skuList = response.data.skuList
          this.spec = this.skuList.length>0?'more':'one'
          let galleryArr =this.form .gallery.split(',')
          for (let i = 0; i < galleryArr.length; i++) {
            if (galleryArr[i] != '') {
              this.galleryList.push({
                id: galleryArr[i],
                url: this.apiUrl + '/file/getImgStream?idFile=' + galleryArr[i]
              })
            }
          }
          this.setContent(this.form.detail)
          getAttrBy(this.form.idCategory).then(response2 => {
              this.attrKeyList = response2.data.keyList
              this.attrValList = response2.data.valList
          })
        })

      }
      getCategories().then(response => {
        this.categories = response.data
      })
    },
    prev() {
      if (this.active > 0) {
        this.active -= 1
      }
    },
    save() {

      if(!this.idGoods && this.active == 0 ){
        //第一步提交的时候先保存下商品以便获取商品id
        if(this.form.name === '' ||
        this.form.idCategory === '' ||
        this.form.descript === ''){
          this.$message({
            message: '请输入必要的商品项目',
            type: 'error'
          })
          return
        }
        goodsApi.saveBaseInfo({
          name: this.form.name,
          idCategory: this.form.idCategory,
          descript: this.form.descript,
          isNew: this.form.isNew,
          isHot:this.form.isHot
        }).then( response => {
          this.idGoods = response.data
        })
        getAttrBy(this.form.idCategory).then(response2 => {
          this.attrKeyList = response2.data.keyList
          this.attrValList = response2.data.valList
        })
      }
      if (this.active < 3) {
        this.active++
        return
      }
      const content = this.getContent()
      const gallery = this.getGallery()
      if(this.spec === 'more'){
        //如果商品配置多规格，则删除单规格配置
        this.form.price =''
        this.form.marketingPrice =''
        this.form.stock = ''
      }
      goodsApi.save({
        name: this.form.name,
        pic: this.form.pic,
        gallery: gallery,
        idCategory: this.form.idCategory,
        descript: this.form.descript,
        detail: content,
        stock: this.form.stock,
        price: this.form.price,
        isDelete: this.form.isDelete,
        isOnSale: this.form.isOnSale,
        id: this.idGoods,
        isNew: this.form.isNew,
        isHot:this.form.isHot
      }).then(response => {
        this.$message({
          message: this.$t('common.optionSuccess'),
          type: 'success'
        })
        this.$router.push('/goods')
      })
    },

    getGallery() {
      let gallery = ''
      for (let i = 0; i < this.galleryList.length; i++) {
        if (i == 0) {
          gallery = this.galleryList[i].id
        } else {
          gallery += (',' + this.galleryList[i].id)
        }

      }
      return gallery
    },
    handleRemove: function (file, fileList) {
      for (var i = 0; i < this.galleryList.length; i++) {
        if (this.galleryList[i].id === file.id) {
          this.galleryList.splice(i, 1)
        }
      }

    },
    handleUploadPicSuccess(response, raw) {
      if (response.code === 20000) {
        this.form.pic = response.data.realFileName
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    },
    handleUploadGallerySuccess(response, raw) {
      if (response.code === 20000) {
        this.galleryList.push(
          {
            id: response.data.realFileName,
            url: this.apiUrl + '/file/getImgStream?idFile=' + response.data.realFileName
          }
        )
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    },

    initTinymce() {
      const _this = this
      window.tinymce.init({
        value: '',
        language: this.language,
        selector: `#${this.tinymceId}`,
        height: this.height,
        body_class: 'panel-body ',
        object_resizing: false,
        toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar,
        menubar: this.menubar,
        plugins: plugins,
        end_container_on_empty_block: true,
        powerpaste_word_import: 'clean',
        code_dialog_height: 450,
        code_dialog_width: 1000,
        advlist_bullet_styles: 'square',
        advlist_number_styles: 'default',
        imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
        default_link_target: '_blank',
        link_title: false,
        nonbreaking_force_tab: true, // inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
        init_instance_callback: editor => {
          if (_this.value) {
            editor.setContent(_this.value)
          }
          _this.hasInit = true
          editor.on('NodeChange Change KeyUp SetContent', () => {
            this.hasChange = true
            const content = editor.getContent()
            this.form.content = content
          })
        },
        setup(editor) {
          editor.on('FullscreenStateChanged', (e) => {
            _this.fullscreen = e.state
          })
        }
      })
    },
    destroyTinymce() {
      const tinymce = window.tinymce.get(this.tinymceId)
      if (this.fullscreen) {
        tinymce.execCommand('mceFullScreen')
      }

      if (tinymce) {
        tinymce.destroy()
      }
    },
    setContent(value) {
      window.tinymce.get(this.tinymceId).setContent(value)
    },
    getContent() {
      return window.tinymce.get(this.tinymceId).getContent()
    },
    imageSuccessCBK(arr) {
      const _this = this
      arr.forEach(v => {
        window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
      })
    },
    tableRowClassName(row, index) {
      if (index === 1) {
        return 'info-row'
      } else if (index === 3) {
        return 'positive-row'
      }
      return ''
    },
    changeAttrKey(val){
      let attrValSel = []
      for(let i=0;i<this.attrValList.length;i++){
        if(this.attrValList[i].idAttrKey === val){
          attrValSel.push(this.attrValList[i])
        }
      }

      this.attrValListSel = attrValSel
      this.attrValSel = attrValSel[0].id
    },
    addAttrKeyFun() {
      this.showAddAttrKey = !this.showAddAttrKey
    },
    addToTag(){
      for(let i=0;i<this.attrValList.length;i++){
        if(this.attrValList[i].id === this.attrValSel){
          this.tags.push(this.attrValList[i])
          break
        }
      }

    },
    submitAttrKeyForm() {
      if( this.attrKeyForm.attrName != ''){
        let duplicationKey = false;
        for(let i=0;i<this.attrKeyList.length;i++){
          if(this.attrKeyForm.attrName === this.attrKeyList[i].attrName){
            duplicationKey = true;
            break;
          }
        }
        if(!duplicationKey){
          const id = this.attrKeyList.length+1;
          this.attrKeyList.push({
            id:id,
            attrName:this.attrKeyForm.attrName
          })
          this.attrKeySel = id;
          this.addAttrKeyFun();
        }
      }
      //提交属性名
      //提交成功后，将属性名自动选择当前属性名
      //提交成功后自动收起添加属性名表单，并且将并单清空重置
    },
    openAddSkuForm() {
      this.tags = []
      this.specDialogFormVisible = true
    },
    closeAddSkuForm() {
      this.specDialogFormVisible = false
    },
    getSkuCode(){
      let code = ''
      let codeName = ''
      for(let i =0; i<this.tags.length;i++){
        if(i===0){
          code = this.tags[i].id
          codeName = this.tags[i].attrVal
        }else{
          code += ',' + this.tags[i].id
          codeName += ',' + this.tags[i].attrVal
        }
      }
      return {code:code,codeName:codeName}
    },
    addSku() {

      if(this.tags.length==0){
        this.$message({
          message: '请选择商品规格',
          type: 'error'
        })
        return
      }
      const skuCodeAndName = this.getSkuCode()
      goodsSku.save({
        idGoods:this.idGoods,
        marketingPrice:this.skuForm.marketingPrice,
        code:skuCodeAndName.code,
        codeName:skuCodeAndName.codeName,
        price:this.skuForm.price,
        stock:this.skuForm.stock
      }).then( response => {

        let sku = response.data
        let updateOldSku = false
        for(let i=0;i<this.skuList.length;i++){
          if(this.skuList[i].id === sku.id){
            this.skuList.splice(i,1,sku)
            updateOldSku = true
            break;
          }
        }
        if(!updateOldSku){
          this.skuList.push(sku)
        }
        this.specDialogFormVisible = false
      })
    },
    removeSku(index) {
      console.log(index)
      let records = this.skuList.splice(index, 1)
      const record = records[0]
      goodsSku.remove(record.id)
    }

  }
}
