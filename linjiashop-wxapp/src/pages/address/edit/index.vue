<template>
    <div>
        <van-nav-bar
                title="编辑收货地址"
                left-text="返回"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-address-edit
                :area-list="areaList"

                :show-delete="showDelete"
                :address-info="addressInfo"
                show-set-default
                show-search-result
                :search-result="searchResult"
                :area-columns-placeholder="columnsPlaceholder"
                @save="onSave"
                @delete="onDelete"
                @change-default="changeDefault"
                @change-detail="onChangeDetail"
        />

    </div>
</template>

<script src="./edit.js"></script>
